package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.data.model.User
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _navigationCommand = MutableStateFlow<String?>(null)
    val navigationCommand: StateFlow<String?> = _navigationCommand.asStateFlow()

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState.Authenticated
                        _navigationCommand.value = AppScreen.HomeGraph.route
                    } else {
                        _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
//                        _countError.value++
                    }
                }
        }
    }

    //see the user details
    private val _user = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _user.asStateFlow()

    init {
        // Check initial auth state

        signOut()
        updateAuthState(auth.currentUser)

        // Listen to Firebase Auth state changes
        viewModelScope.launch {
            auth.addAuthStateListener { firebaseAuth ->
                updateAuthState(firebaseAuth.currentUser)
            }
        }
    }

    private fun updateAuthState(firebaseUser: com.google.firebase.auth.FirebaseUser?) {
        if (firebaseUser != null) {
            _authState.value = AuthState.Authenticated
            _user.value = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                displayName = firebaseUser.displayName,
                photoUrl = firebaseUser.photoUrl?.toString(),
                isEmailVerified = firebaseUser.isEmailVerified
            )
            _navigationCommand.value = AppScreen.HomeGraph.route
        } else {
            _authState.value = AuthState.Unauthenticated
            _user.value = null
            _navigationCommand.value = AppScreen.AuthGraph.route
        }
    }

    fun clearNavigationCommand() {
        _navigationCommand.value = null
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
        _navigationCommand.value = AppScreen.AuthGraph.route
    }

    //update password
    fun sendEmailToChangePassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                }
            }
    }


    //Sign up
    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.Loading
            try {
                auth.createUserWithEmailAndPassword(email, password)
                _signupState.value = SignupState.Success
            } catch (e: Exception) {
                _signupState.value = SignupState.Error("Signup failed: ${e.message}")
            }
        }
    }

    //OTP
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
                delay(1000L)
            }

            _state.update { current ->
                val expired = System.currentTimeMillis() - current.otpGeneratedTime >= 30_000
                if (expired) {
                    current.copy(isExpired = true, isValid = false)
                } else {
                    current
                }
            }
        }
    }


    fun sendOTP(context: Context) {
        val noticationService = NotificationService(context)
        val otp = sendOTP()
        noticationService.showNotification("OTP services", otp)
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


}