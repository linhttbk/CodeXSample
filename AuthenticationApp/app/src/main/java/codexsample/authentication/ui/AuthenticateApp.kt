package codexsample.authentication.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
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
import codexsample.authentication.ui.theme.AuthenticationTheme
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun AuthenticateApp(){
    val navController = rememberNavController()
    val mainViewModel:MainViewModel = hiltViewModel()
    val isUserLogin by mainViewModel.isUserLogin.collectAsState()
    AuthenticationTheme {
        NavHost(navController = navController, startDestination =  if(isUserLogin) Screen.Home.route else  Screen.Login.route){

            composable(route = Screen.Login.route){ _ ->
                val loginViewModel:LoginViewModel = hiltViewModel()
                val signInState:SignInCredentialState by loginViewModel.signInState.collectAsState()
                val context = LocalContext.current
                val googleSignInClient: GoogleSignInClient = remember {
                    context.createGoogleSignInClient()
                }
                val authResultLauncher = rememberLauncherForActivityResult(contract = AuthResultContract(googleSignInClient)) { result ->
                   try {
                       val account = result?.getResult(ApiException::class.java)

                       if(account == null){
                           Toast.makeText(context,"Something wrong",Toast.LENGTH_LONG).show()
                       }else{
                           // auth with firebase
                           val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                           loginViewModel.loginWithCredential(credential)
                       }
                   }catch (ex:ApiException){
                        ex.printStackTrace()
                       Toast.makeText(context,"Error on sign in ${ex.message}",Toast.LENGTH_LONG).show()
                   }

                }
                LoginScreen(signInState,onLoginClicked = { source ->
                    when(source){
                        LoginSource.Google -> {
                            authResultLauncher.launch(1)
                        }
                        LoginSource.Facebook -> {

                        }
                    }
                }, onSignUpClicked = {
                    navController.navigate(Screen.SignUp.route)
                })
                LaunchedEffect(signInState) {
                    if(signInState is SignInCredentialState.Error){
                        Toast.makeText(context,"Error on sign in ${(signInState as SignInCredentialState.Error).ex.message}",Toast.LENGTH_LONG).show()
                    }
                }
            }
            composable(route = Screen.Home.route){
                val homeViewModel:HomeViewModel = hiltViewModel()
                val userEmail by homeViewModel.currentUserEmail.collectAsState(initial = null)
                val context = LocalContext.current
                HomeScreen(userEmail, onSignOutClicked = {
                    homeViewModel.signOut(context)
                })
            }
            composable(route=Screen.SignUp.route){
                SignUpScreen(onLoginClicked = {

                }, onSignInClicked = {
                    navController.navigate(Screen.Login.route)
                })
            }
        }
    }
    LaunchedEffect(key1 = isUserLogin) {
        if(isUserLogin){
           if(navController.currentDestination?.route != Screen.Home.route){
               navController.navigate(Screen.Home.route)
           }
        }else{
            if(navController.currentDestination?.route == Screen.Home.route){
                navController.navigate(Screen.Login.route)
            }
        }
    }
}