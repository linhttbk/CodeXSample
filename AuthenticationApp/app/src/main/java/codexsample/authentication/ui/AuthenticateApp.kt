package codexsample.authentication.ui

import android.credentials.GetCredentialException.TYPE_NO_CREDENTIAL
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import codexsample.authentication.MainViewModel
import codexsample.authentication.ui.common.AuthResultContract
import codexsample.authentication.ui.common.SignInCredentialState
import codexsample.authentication.ui.common.createGoogleSignInClient
import codexsample.authentication.ui.features.home.HomeScreen
import codexsample.authentication.ui.features.home.HomeViewModel
import codexsample.authentication.ui.features.login.LoginScreen
import codexsample.authentication.ui.features.login.LoginSource
import codexsample.authentication.ui.features.login.LoginViewModel
import codexsample.authentication.ui.features.signup.SignUpScreen
import codexsample.authentication.ui.features.signup.SignUpUIState
import codexsample.authentication.ui.features.signup.SignUpViewModel
import codexsample.authentication.ui.theme.AuthenticationTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthenticateApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val isUserLogin by mainViewModel.isUserLogin.collectAsState()
    AuthenticationTheme {
        NavHost(
            navController = navController,
            startDestination = if (isUserLogin) Screen.Home.route else Screen.Login.route
        ) {

            composable(route = Screen.Login.route) { _ ->
                val loginViewModel: LoginViewModel = hiltViewModel()
                val signInState: SignInCredentialState by loginViewModel.signInState.collectAsState()
                val context = LocalContext.current
                LoginScreen(signInState, onLoginClicked = { source ->
                    when (source) {
                        LoginSource.Google -> {
                            loginViewModel.signInWithGoogle(context)
                        }

                        LoginSource.Facebook -> {

                        }
                    }
                }, onSignUpClicked = {
                    navController.navigate(Screen.SignUp.route)
                })
                LaunchedEffect(signInState) {
                    val signInUIStateError = signInState as? SignInCredentialState.Error
                    if (signInUIStateError != null) {
                        if (signInUIStateError.ex is NoCredentialException) {
                            loginViewModel.signInWithGoogle(context, false)
                        } else {
                            Toast.makeText(
                                context,
                                "Error on sign in ${signInUIStateError.ex.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            composable(route = Screen.Home.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val homeTabSelected by homeViewModel.selectedHomeTab.collectAsState()
                HomeScreen(homeTabSelected, onTabSelected = { selectedTab ->
                    homeViewModel.updateHomeTabSelected(selectedTab)
                })
            }
            composable(route = Screen.SignUp.route) {
                val signUpViewModel: SignUpViewModel = hiltViewModel()
                val signUpUIState by signUpViewModel.signUpUIState.collectAsState()
                val context = LocalContext.current
                SignUpScreen(
                    shouldShowLoading = signUpUIState == SignUpUIState.Loading,
                    onLoginClicked = {

                    },
                    onSignInClicked = {
                        navController.navigate(Screen.Login.route)
                    },
                    onCreateAccountClicked = { name, email, password ->
                        signUpViewModel.signUpWithEmailAndPassword(name, email, password)
                    })
                LaunchedEffect(key1 = signUpUIState) {
                    if (signUpUIState is SignUpUIState.Error) {
                        Toast.makeText(
                            context,
                            (signUpUIState as SignUpUIState.Error).exception.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = isUserLogin) {
        if (isUserLogin) {
            if (navController.currentDestination?.route != Screen.Home.route) {
                navController.navigate(Screen.Home.route)
            }
        } else {
            if (navController.currentDestination?.route == Screen.Home.route) {
                navController.navigate(Screen.Login.route)
            }
        }
    }
}