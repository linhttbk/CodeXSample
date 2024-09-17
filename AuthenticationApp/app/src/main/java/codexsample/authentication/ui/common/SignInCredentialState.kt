package codexsample.authentication.ui.common

sealed class SignInCredentialState {
    data object Initialized : SignInCredentialState()
    data object Loading : SignInCredentialState()
    data class Error(val ex:Exception) : SignInCredentialState()
    data object Success: SignInCredentialState()
}