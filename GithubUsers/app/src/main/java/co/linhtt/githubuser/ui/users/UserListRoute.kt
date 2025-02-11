package co.linhtt.githubuser.ui.users

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun UserListRoute(navHostController:NavHostController) {
    val viewModel:UserListViewModel = hiltViewModel()
    val users = viewModel.users.collectAsLazyPagingItems()
    UserListScreen(users) {
        //TODO handle show user details
    }
}