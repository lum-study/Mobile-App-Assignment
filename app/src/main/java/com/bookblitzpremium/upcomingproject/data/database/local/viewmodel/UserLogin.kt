package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.credentials.GetCredentialException
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.MainActivity
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.ui.components.showNotification
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.getOrNull
import kotlin.collections.mapIndexed
import kotlin.jvm.java

class UserLogin: ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Define a StateFlow for countError to be reactive
    private val _countError = MutableStateFlow(0) // Use MutableStateFlow here

    var countError: StateFlow<Int> = _countError // Expose it as StateFlow

    init {
        ChangeState2()
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if(auth.currentUser==null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun VerifyEmpty(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                _authState.value = AuthState.Error("Email can't be empty")
                false
            }
            password.isEmpty() -> {
                _authState.value = AuthState.Error("Password can't be empty")
                false
            }
            else -> true
        }
    }

    fun login(email: String, password: String){

        if (!VerifyEmpty(email, password)) return

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->   //return the complete task
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    ChangeState()
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                    _countError.value++
                }
            }
    }

    fun updateErrorToZero(){
        _countError.update { 0 }
    }

    fun ChangeState2() {
        _authState.update {
            AuthState.Unauthenticated
        }
    }

    fun ChangeState() {
        _authState.update {
            AuthState.Yes
        }
    }

    fun signup(email : String,password : String){

        if (!VerifyEmpty(email, password)) return

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    private val _state = MutableStateFlow(OtpState())
    val state = _state.asStateFlow()

    internal fun sendOTP(length: Int = 4): String {
        val digits = "0123456789"
        val generated = (1..length).map { digits.random() }.joinToString("")
        val now = System.currentTimeMillis()

        _state.update {
            it.copy(
                generatedOtp = generated,
                otpGeneratedTime = now,
                isExpired = false,
                isValid = null,
                code = List(4) { null }, // reset entered code
                focusedIndex = 0
            )
        }

        startOtpTimer() // Start the countdown
        return generated
    }

    private fun startOtpTimer() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            val duration = 30_000L
            val endTime = startTime + duration

            while (System.currentTimeMillis() < endTime) {
                val secondsLeft = (endTime - System.currentTimeMillis()) / 1000
                Log.d("OtpTimer", "Seconds left: $secondsLeft")
                delay(1000L)
            }

            _state.update { current ->
                val expired = System.currentTimeMillis() - current.otpGeneratedTime >= 30_000
                if (expired) {
                    Log.d("OtpTimer", "OTP expired after 30 seconds.")
                    current.copy(isExpired = true, isValid = false)
                } else {
                    current
                }
            }
        }
    }




    fun sendOTP(context: Context) {
        val otp = sendOTP()
        showNotification(context, otp)
    }


    fun onAction(action: OtpAction) {
        when (action) {
            is OtpAction.OnChangeFieldFocused -> {
                _state.update { it.copy(focusedIndex = action.index) }
            }
            is OtpAction.OnEnterNumber -> {
                enterNumber(action.number, action.index)
            }
            OtpAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex)
                _state.update {
                    it.copy(
                        code = it.code.mapIndexed { index, number ->
                            if (index == previousIndex) null else number
                        },
                        focusedIndex = previousIndex
                    )
                }
            }
        }
    }

    fun updateStateOfOTP(){
        _state.update {
            OtpState() // Reset everything to default values
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val otp = _state.value.generatedOtp
        val newCode = state.value.code.mapIndexed { currentIndex, currentNumber ->
            if (currentIndex == index) number else currentNumber
        }

        val wasNumberRemoved = number == null
        _state.update {
            it.copy(
                code = newCode,
                focusedIndex = if (wasNumberRemoved || it.code.getOrNull(index) != null) {
                    it.focusedIndex
                } else {
                    getNextFocusedTextFieldIndex(it.code, it.focusedIndex)
                },
                isValid = if (newCode.none { it == null } && !it.isExpired) {
                    newCode.joinToString("") == otp
                } else null
            )
        }
    }


    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return (currentIndex?.minus(1))?.takeIf { it >= 0 }
    }


    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 3) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    val user = auth.currentUser

    fun changePassword(
        currentEmail: String,
        currentPassword: String,
        newPassword: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {

        if (user != null) {
            val credential = EmailAuthProvider.getCredential(currentEmail, currentPassword)

            // Step 1: Re-authenticate the user
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        // Step 2: Update the password
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    onSuccess()
                                } else {
                                    onFailure(updateTask.exception?.message ?: "Password update failed.")
                                }
                            }
                    } else {
                        onFailure(reauthTask.exception?.message ?: "Re-authentication failed.")
                    }
                }
        } else {
            onFailure("No user is currently logged in.")
        }
    }

    //update password
    fun sendEmailToChangePassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FirebaseAuth", "Password reset email sent.")
                } else {
                    Log.e("FirebaseAuth", "Failed to send password reset email: ${task.exception?.message}")
                }
            }
    }



    //over 30 second
}

