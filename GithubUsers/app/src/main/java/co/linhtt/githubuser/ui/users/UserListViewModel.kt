package co.linhtt.githubuser.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import co.linhtt.domain.usecase.GetAllUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class UserListViewModel @Inject constructor(
    getAllUsers: GetAllUsers
) : ViewModel() {
    val users = getAllUsers.invoke().cachedIn(viewModelScope)
}