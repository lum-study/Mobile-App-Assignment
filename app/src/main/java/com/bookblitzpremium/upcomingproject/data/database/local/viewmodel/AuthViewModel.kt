package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteUserRepository
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.data.model.PasswordResetState
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


data class User(
    val email: String,
    val password:String,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val remoteUserViewModel: RemoteUserRepository
) : ViewModel() {

    // Authentication state
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Navigation command
    private val _newNavigationCommand = MutableStateFlow<Boolean>(false)
    val newNavigationCommand: StateFlow<Boolean> = _newNavigationCommand.asStateFlow()

    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    // User details
    private val _user = MutableStateFlow(User())
    val userDetails: StateFlow<User> = _user.asStateFlow()

    fun updateEmails(email: String) {
        _user.value = _user.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _user.value = _user.value.copy(password = password)
    }

    fun clearEmailPassword(){
        _user.value = User()
    }

    init {
//        auth.signOut()
        checkAuthStatus()
    }

    fun signOut() {
        auth.signOut()
        clearEmailPassword()
        clearAuthenticatedState()
        clearSignUpState()
        clearNavigationCommand()
        resetPasswordState()
    }

    val uid = getUserId()

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
            _newNavigationCommand.value = false
        } else {
            _authState.value = AuthState.Authenticated
            _newNavigationCommand.value = true
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    private val _authError = MutableStateFlow<String?>(null)
    val authError = _authError.asStateFlow()


    fun login(email: String, password: String, onClick: () -> Unit = {}, isTablet: Boolean = false) {
        viewModelScope.launch {
            _authError.value = null
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                val userId = authResult.user?.uid
                    ?: throw IllegalStateException("Failed to authenticate user")
                _authState.value = AuthState.Authenticated
                if(isTablet){
                    onClick()
                }else{
                    _newNavigationCommand.value = true
                }
                _authState.value = AuthState.Triggerable
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _authError.value = "Invalid email or password"
                _authState.value = AuthState.Error("Invalid email or password")
            } catch (e: FirebaseAuthInvalidUserException) {
                _authError.value = "No account exists for this email"
                _authState.value = AuthState.Error("No account exists for this email")
            } catch (e: Exception) {
                _authError.value = e.localizedMessage ?: "Failed to login"
                _authState.value = AuthState.Error("Login failed: ${e.localizedMessage}")
            }
        }
    }

    fun setPath(){
        _newNavigationCommand.value = true
    }

    fun setAuthError(message: String?) {
        _authError.value = message
    }

    // Sign up
    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    fun performSignup(email: String, password: String, gender: String): Job {
        return viewModelScope.launch {
            _signupState.value = SignupState.Loading
            try {
                // First portion: Signup
                val customId = signup(email, password)
                if (customId.isEmpty()) {
                    throw Exception("Signup failed: Empty user ID")
                }
                val username = email.substringBefore("@")
                val user = User(id = customId, name = username, email = email, password = password, gender = gender)
                remoteUserViewModel.addUserSpecila(customId, user)
//                remoteUserViewModel.updateUserGender(customId, user.gender)

                _signupState.value = SignupState.Success
            } catch (e: FirebaseAuthUserCollisionException) {
                _signupState.value = SignupState.Error("Email is already registered")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _signupState.value = SignupState.Error("Invalid email format")
            } catch (e: Exception) {
                _signupState.value = SignupState.Error("Signup failed: ${e.message ?: "Unknown error"}")
            }
        }
    }

    private suspend fun signup(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user ?: throw Exception("User creation failed: No user returned")
        return user.uid
    }

    fun setSignupError(message: String) {
        _signupState.value = SignupState.Error(message)
    }

    // Password reset state
    private val _passwordResetState = MutableStateFlow<PasswordResetState>(PasswordResetState.Idle)
    val passwordResetState: StateFlow<PasswordResetState> = _passwordResetState.asStateFlow()

    fun sendPasswordResetEmail(email: String) {
        viewModelScope.launch {
            _passwordResetState.value = PasswordResetState.Loading
            try {
                // Send the password reset email
                auth.sendPasswordResetEmail(email).await()
                _passwordResetState.value = PasswordResetState.Success

            } catch (e: FirebaseAuthException) {
                when (e.errorCode) {
                    "ERROR_USER_NOT_FOUND" -> {
                        _passwordResetState.value =
                            PasswordResetState.Error("No account found with this email")
                    }

                    "ERROR_INVALID_EMAIL" -> {
                        _passwordResetState.value = PasswordResetState.Error("Invalid email format")
                    }

                    else -> {
                        _passwordResetState.value =
                            PasswordResetState.Error("Unknown error: ${e.message}")
                    }
                }
                Log.e("PasswordResetViewModel", "Error: ${e.message}")
            }

        }
    }

    fun resetPasswordState() {
        _passwordResetState.value = PasswordResetState.Idle
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

    fun updateStateOfOTP() {
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
        if (currentFocusedIndex == null) {
            return null
        }
        if (currentFocusedIndex == 3) {
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
            if (index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if (number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }


    fun clearNavigationCommand() {
        _newNavigationCommand.value = false
    }

    fun clearAuthenticatedState() {
        _authState.value = AuthState.Unauthenticated
    }

    fun clearSignUpState() {
        _signupState.value = SignupState.Idle
    }

}