package com.bookblitzpremium.upcomingproject.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInUser:ViewModel(){

    private val _state = MutableStateFlow(SignInState())   //data class

    val state = _state.asStateFlow()

    fun onSignInResult(result:SignInResult){
        _state.update {
            it.copy(
                isSignInSuccsful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }


    fun resetSatte(){
        _state.update {
            SignInState()
        }
    }


}