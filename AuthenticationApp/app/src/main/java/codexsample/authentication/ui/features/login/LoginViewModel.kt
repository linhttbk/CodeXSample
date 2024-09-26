package codexsample.authentication.ui.features.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codexsample.authentication.R
import codexsample.authentication.ui.common.SignInCredentialState
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _signInState: MutableStateFlow<SignInCredentialState> =
        MutableStateFlow(SignInCredentialState.Initialized)
    val signInState: StateFlow<SignInCredentialState>
        get() = _signInState

    fun loginWithCredential(credential: AuthCredential) {
        _signInState.value = SignInCredentialState.Loading
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
            _signInState.value = SignInCredentialState.Success
        }.addOnFailureListener {
            _signInState.value = SignInCredentialState.Error(it)
        }
    }

    fun signInWithGoogle(context: Context,requiredAuthorized:Boolean = true  ) {
        val googleIdOption = GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(requiredAuthorized)
            .setServerClientId(context.getString(R.string.default_web_client_id)).build()
        val request = GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
        val credentialManager = CredentialManager.create(context)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val credential = credentialManager.getCredential(context, request)
                handleSignIn(credential)
            } catch (ex: GetCredentialException) {
                ex.printStackTrace()
                _signInState.value = SignInCredentialState.Error(ex)
            }
        }

    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        val authCredential = GoogleAuthProvider.getCredential(idToken, null)
                        loginWithCredential(authCredential)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        _signInState.value = SignInCredentialState.Error(ex)
                    }
                }else{
                    throw RuntimeException("Unsupported credential type")
                }
            }

            else -> {
                throw RuntimeException("Unsupported credential type")
            }
        }
    }
}