package codexsample.authentication.ui.features.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignUpViewModel @Inject constructor() :ViewModel(){
    private val _signUpUIState = MutableStateFlow<SignUpUIState>(SignUpUIState.Idle)
    val signUpUIState:StateFlow<SignUpUIState>
        get() = _signUpUIState

    fun signUpWithEmailAndPassword(name:String,email:String,password:String){
        _signUpUIState.value = SignUpUIState.Loading
        // after sign up, screen was closed -> onCleared called -> viewModelScope was canceled
        CoroutineScope(Dispatchers.Default).launch {
            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                val userId  = it.user?.uid
                if(userId != null){
                    updateNameForUser(userId,name)
                }else{
                    _signUpUIState.value = SignUpUIState.Error(Exception("Unknown Error"))
                }
            }.addOnFailureListener {
                _signUpUIState.value = SignUpUIState.Error(it)
            }
        }
    }
    private fun updateNameForUser(userId:String,name:String){
        Firebase.firestore.collection("users").document(userId).set(mapOf(
            "name" to name
        ), SetOptions.merge())
    }
}