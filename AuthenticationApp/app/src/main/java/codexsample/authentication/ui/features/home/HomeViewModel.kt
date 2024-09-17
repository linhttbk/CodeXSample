package codexsample.authentication.ui.features.home

import android.content.Context
import androidx.lifecycle.ViewModel
import codexsample.authentication.ui.common.createGoogleSignInClient
import codexsample.authentication.ui.common.firebaseUser
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor() :ViewModel() {
    val currentUserEmail :Flow<String?> = firebaseUser().mapLatest { firebaseUser ->
        firebaseUser?.email
    }.flowOn(Dispatchers.Default)

    fun signOut(context: Context){
        FirebaseAuth.getInstance().signOut()
        if(GoogleSignIn.getLastSignedInAccount(context)!= null){
            // sign out google account
            context.createGoogleSignInClient().signOut()
        }
    }
}