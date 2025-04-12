package com.bookblitzpremium.upcomingproject.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.bookblitzpremium.upcomingproject.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import java.util.concurrent.Executor

class GoogleUiClient(
    private val context:Context,
    private val oneTapLCient:SignInClient,  //for signin
){

    private val auth = Firebase.auth
    suspend fun signIn() : IntentSender?{
        val result = try{
            oneTapLCient.beginSignIn(
                buildSignRequest()
            ).await()
        }catch (e :Exception){
            e.printStackTrace()

            if(e is CancellationException) throw e
            null
        }
        return  result?.pendingIntent?.intentSender
    }

    suspend fun getSingResultFromIntent(intent: Intent) : SignInResult{
        val credentials = oneTapLCient.getSignInCredentialFromIntent(intent)
        val googleidToken = credentials.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleidToken,null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePicture = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = null
            )
        }


    }

    suspend fun SignOut(){
        try {
            oneTapLCient.signOut().await()
            auth.signOut()
        }catch (e :Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e

        }
    }

    fun getSignedUser() :UserData?= auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePicture = photoUrl.toString()
        )
    }

    private fun buildSignRequest(): BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}