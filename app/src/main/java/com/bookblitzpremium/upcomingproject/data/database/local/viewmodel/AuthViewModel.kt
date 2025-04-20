package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.data.model.PasswordChangeState
import com.bookblitzpremium.upcomingproject.data.model.PasswordResetState
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.data.model.User
import com.bookblitzpremium.upcomingproject.data.model.VerifyEmail
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.actionCodeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    // Authentication state
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // User details
    private val _user = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _user.asStateFlow()

    // Navigation command
    private val _navigationCommand = MutableStateFlow(false) // Set to false initially
    val navigationCommand: StateFlow<Boolean> = _navigationCommand.asStateFlow()

    init {
        signOut()
        Log.e("Login", "Start user: ${_authState.value}")
        checkAuthStatus()
        Log.e("Login", "End user: ${_authState.value}")
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
            _navigationCommand.value = false // Fixed: Assignment instead of comparison
        } else {
            _authState.value = AuthState.Authenticated
            _navigationCommand.value = true // Fixed: Assignment instead of comparison
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    updateAuthState(result.user)
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error("Login failed: No user returned")
                }
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("INVALID_EMAIL") == true -> "Invalid email format"
                    e.message?.contains("WRONG_PASSWORD") == true -> "Incorrect password"
                    e.message?.contains("NETWORK") == true -> "Network error, please try again"
                    else -> e.message ?: "Something went wrong"
                }
                _authState.value = AuthState.Error(errorMessage)
            } finally {
                _authState.value = AuthState.Unauthenticated // Reset to Idle
            }
        }
    }

    private fun updateAuthState(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            _authState.value = AuthState.Authenticated
            _user.value = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email,
                displayName = firebaseUser.displayName,
                photoUrl = firebaseUser.photoUrl?.toString(),
            )
            _navigationCommand.value = true
        } else {
            _authState.value = AuthState.Unauthenticated
            _user.value = null
            _navigationCommand.value = false
        }
    }

    fun NavigationCommandToHome() {
        _navigationCommand.value = true // Fixed: Set to true to trigger navigation
    }

    fun clearNavigationCommand() {
        _navigationCommand.value = false // Fixed: Set to false to reset
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
        _navigationCommand.value = false
    }

    // Sign up
    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.Loading
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _signupState.value = SignupState.Success
                _navigationCommand.value = true

            } catch (e: FirebaseAuthUserCollisionException) {
                _signupState.value = SignupState.Error("Email is already registered")
            } catch (e: FirebaseAuthWeakPasswordException) {
                _signupState.value = SignupState.Error("Password is too weak")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _signupState.value = SignupState.Error("Invalid email format")
            } catch (e: Exception) {
                _signupState.value = SignupState.Error("Signup failed: ${e.message}")
            } finally {
                delay(2000)
                _signupState.value = SignupState.Idle
            }
        }
    }

    private val _verifyEmail = MutableStateFlow<VerifyEmail>(VerifyEmail.Idle)
    val verifyEmail: StateFlow<VerifyEmail> = _verifyEmail.asStateFlow()

    fun existsEmail(email: String) {
        viewModelScope.launch {
            _verifyEmail.value = VerifyEmail.Loading
            try {
                val result = auth.fetchSignInMethodsForEmail(email).await()
                val methods = result.signInMethods ?: emptyList()

                if (methods.isEmpty()) {

                    _verifyEmail.value = VerifyEmail.Error("No account found with this email")
                    Log.d("AuthDebug", "Empty")
                } else {
                    _verifyEmail.value = VerifyEmail.Verified
                    Log.d("AuthDebug", "Email exists with methods: $methods")
                }
            } catch (e: Exception) {
                _verifyEmail.value = VerifyEmail.Error(e.localizedMessage ?: "Unknown error occurred")
            }
        }
    }

    fun resetVerifyEmailState(){
        _verifyEmail.value = VerifyEmail.Idle
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
                _navigationCommand.value = true
                Log.d("PasswordResetViewModel", "Password reset email sent to $email")
            } catch (e: FirebaseAuthInvalidUserException) {
                _passwordResetState.value = PasswordResetState.Error("No account found with this email")
                Log.e("PasswordResetViewModel", "Error: No account found with this email")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _passwordResetState.value = PasswordResetState.Error("Invalid email format")
                Log.e("PasswordResetViewModel", "Error: Invalid email format")
            } catch (e: Exception) {
                _passwordResetState.value = PasswordResetState.Error("Failed to send password reset email: ${e.message}")
                Log.e("PasswordResetViewModel", "Error sending password reset email: ${e.message}")
            }
        }
    }

    fun resetState() {
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



//
//    // Authentication state
//    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
//    val authState: StateFlow<AuthState> = _authState.asStateFlow()
//
//    //see the user details
//    private val _user = MutableStateFlow<User?>(null)
//    val userDetails: StateFlow<User?> = _user.asStateFlow()
//
//    // Navigation command
//    private val _navigationCommand = MutableStateFlow<Boolean>(true) // Simplified to non-nullable
//    val navigationCommand: StateFlow<Boolean> = _navigationCommand.asStateFlow()
//
//        fun checkAuthStatus() {
//            if (auth.currentUser == null) {
//                _authState.value = AuthState.Unauthenticated
//                _navigationCommand.value == true
//            } else {
//                _authState.value = AuthState.Authenticated
//                _navigationCommand.value == false
//            }
//        }
//
//    init {
//        signOut()
//        Log.e("Login","Start user:" + _authState.value.toString())
//        checkAuthStatus()
//        Log.e("Login","End user:" +_authState.value.toString())
//    }
//
//
//    fun login(email: String, password: String) {
//        viewModelScope.launch {
//            _authState.value = AuthState.Loading
//            try {
//                val result = auth.signInWithEmailAndPassword(email, password).await()
//                if (result.user != null) {
//                    _authState.value = AuthState.Authenticated
//                    _navigationCommand.value = false
//                } else {
//                    _authState.value = AuthState.Error("Login failed: No user returned")
//                }
//            } catch (e: Exception) {
//                val errorMessage = when {
//                    e.message?.contains("INVALID_EMAIL") == true -> "Invalid email format"
//                    e.message?.contains("WRONG_PASSWORD") == true -> "Incorrect password"
//                    e.message?.contains("NETWORK") == true -> "Network error, please try again"
//                    else -> e.message ?: "Something went wrong"
//                }
//                _authState.value = AuthState.Error(errorMessage)
//            }
//        }
//    }
//
//    private fun updateAuthState(firebaseUser: FirebaseUser?) {
//        if (firebaseUser != null) {
//            _authState.value = AuthState.Authenticated
//            _user.value = User(
//                uid = firebaseUser.uid,
//                email = firebaseUser.email,
//                displayName = firebaseUser.displayName,
//                photoUrl = firebaseUser.photoUrl?.toString(),
//                isEmailVerified = firebaseUser.isEmailVerified
//            )
//            _navigationCommand.value = false
//        } else {
//            _authState.value = AuthState.Unauthenticated
//            _user.value = null
//            _navigationCommand.value = true
//        }
//    }
//
//    fun NavigationCommandToHome() {
//        _navigationCommand.value = false
//    }
//
//    fun clearNavigationCommand() {
//        _navigationCommand.value = true
//    }
//
//    fun signOut() {
//        auth.signOut()
//        _authState.value = AuthState.Unauthenticated
//        _navigationCommand.value = true
//    }
//
//    // Password reset state
//    private val _passwordResetState = MutableStateFlow<PasswordResetState>(PasswordResetState.Idle)
//    val passwordResetState: StateFlow<PasswordResetState> = _passwordResetState.asStateFlow()
//
//    // Password change state
//    private val _passwordChangeState = MutableStateFlow<PasswordChangeState>(PasswordChangeState.Idle)
//    val passwordChangeState: StateFlow<PasswordChangeState> = _passwordChangeState.asStateFlow()
//
//    //update password
//    fun sendEmailToChangePassword(email: String){
//        auth.sendPasswordResetEmail(email)
//            .addOnCompleteListener { task ->
//                _passwordChangeState.value = PasswordChangeState.Loading
//                try {
//                    auth.sendPasswordResetEmail(email)
//                    _passwordResetState.value = PasswordResetState.Success
//                } catch (e: Exception) {
//                    _signupState.value = SignupState.Error("Signup failed: ${e.message}")
//                }
//            }
//    }
//
//    // Email verification state
//    private val _verifyEmail = MutableStateFlow<VerifyEmail>(VerifyEmail.Idle)
//    val verifyEmail: StateFlow<VerifyEmail> = _verifyEmail.asStateFlow()
//
//    fun verifyEmailAddress(user: FirebaseUser) {
//        viewModelScope.launch {
//            _verifyEmail.value = VerifyEmail.Loading
//            try {
//                user.sendEmailVerification().await()
//                _verifyEmail.value = VerifyEmail.Success
//                _navigationCommand.value = false // Trigger navigation
//            } catch (e: Exception) {
//                _verifyEmail.value = VerifyEmail.Error("Email verification failed: ${e.message}")
//            }
//        }
//    }
//
//
//    //Sign up
//    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
//    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()
//
//    fun signup(email: String, password: String) {
//        viewModelScope.launch {
//            _signupState.value = SignupState.Loading
//            try {
//                val signInMethods = auth.fetchSignInMethodsForEmail(email).await()
//                if (signInMethods.signInMethods?.isNotEmpty() == true) {
//                    _signupState.value = SignupState.Error("Email is already registered")
//                    return@launch
//                }
//
//                auth.createUserWithEmailAndPassword(email, password)
//                _signupState.value = SignupState.Success
//                verifyEmailAddress(auth.currentUser)
//            } catch (e: Exception) {
//                _signupState.value = SignupState.Error("Signup failed: ${e.message}")
//            }
//        }
//    }