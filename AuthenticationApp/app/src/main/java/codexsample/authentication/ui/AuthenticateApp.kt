package codexsample.authentication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import codexsample.authentication.ui.features.login.LoginScreen
import codexsample.authentication.ui.features.signup.SignUpScreen
import codexsample.authentication.ui.theme.AuthenticationTheme

@Composable
fun AuthenticateApp(){
    val navController = rememberNavController()
    AuthenticationTheme {
        NavHost(navController = navController, startDestination = Screen.Login.route){
            composable(route = Screen.Login.route){ _ ->
                LoginScreen(onLoginClicked = {

                }, onSignUpClicked = {
                    navController.navigate(Screen.SignUp.route)
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
}