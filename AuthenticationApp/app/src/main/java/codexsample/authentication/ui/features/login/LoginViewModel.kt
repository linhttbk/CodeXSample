package codexsample.authentication.ui.features.login

import androidx.lifecycle.ViewModel
import codexsample.authentication.ui.common.SignInCredentialState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor() :ViewModel() {
    private val _signInState : MutableStateFlow<SignInCredentialState>  = MutableStateFlow(SignInCredentialState.Initialized)
    val signInState: StateFlow<SignInCredentialState>
        get() = _signInState
    fun loginWithCredential(credential:AuthCredential){
        _signInState.value = SignInCredentialState.Loading
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener {
            _signInState.value = SignInCredentialState.Success
        }.addOnFailureListener {
            _signInState.value  = SignInCredentialState.Error(it)
        }
    }
}