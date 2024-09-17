package codexsample.authentication.ui.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun firebaseUser() = callbackFlow{
    val firebaseAuth = FirebaseAuth.getInstance()
    val authStateChange = AuthStateListener { firebaseUser -> trySend(firebaseUser.currentUser) }
    firebaseAuth.addAuthStateListener(authStateChange)
    awaitClose {
        firebaseAuth.removeAuthStateListener(authStateChange)
    }
}