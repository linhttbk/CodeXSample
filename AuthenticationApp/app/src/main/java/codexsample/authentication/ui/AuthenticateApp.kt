package codexsample.authentication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import codexsample.authentication.ui.features.login.LoginScreen
import codexsample.authentication.ui.theme.AuthenticationTheme

@Composable
fun AuthenticateApp(){
    val navController = rememberNavController()
    AuthenticationTheme {
        NavHost(navController = navController, startDestination = Screen.Login.route){
            composable(route = Screen.Login.route){ _ ->
                LoginScreen(onLoginClicked = {

                })
            }
        }
    }
}