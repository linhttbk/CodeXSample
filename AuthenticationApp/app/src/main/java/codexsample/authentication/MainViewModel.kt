package codexsample.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import codexsample.authentication.ui.common.firebaseUser
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor():ViewModel() {
    val isUserLogin :StateFlow<Boolean> = firebaseUser().mapLatest { user ->
        user != null
    }.stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = FirebaseAuth.getInstance().currentUser != null )
}