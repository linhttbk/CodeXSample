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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor() :ViewModel() {
    val currentUserEmail :Flow<String?> = firebaseUser().mapLatest { firebaseUser ->
        firebaseUser?.email
    }.flowOn(Dispatchers.Default)
    private val _selectedHomeTab = MutableStateFlow<HomeTab>(HomeTab.Tasks)
    val selectedHomeTab :StateFlow<HomeTab>
        get() = _selectedHomeTab

    fun updateHomeTabSelected(homeTab: HomeTab){
        _selectedHomeTab.value = homeTab
    }

    fun signOut(context: Context){
        FirebaseAuth.getInstance().signOut()
        if(GoogleSignIn.getLastSignedInAccount(context)!= null){
            // sign out google account
            context.createGoogleSignInClient().signOut()
        }
    }
}