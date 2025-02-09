package co.linhtt.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import co.linhtt.data.db.UserDatabase
import co.linhtt.data.remote.UserRemoteMediator
import co.linhtt.domain.model.GithubUser
import co.linhtt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProvider @Inject constructor(
    private val database: UserDatabase,
    private val remoteMediator: UserRemoteMediator
) : UserRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getAllUsers(): Flow<PagingData<GithubUser>> {
        return Pager(config = PagingConfig(
            pageSize = UserRemoteMediator.ITEMS_PER_PAGE, enablePlaceholders = false
        ), remoteMediator = remoteMediator,
            pagingSourceFactory = {
                database.userDao().getUsers()
            }).flow.map { pagingData ->
            pagingData.map { userEntity ->
                GithubUser(
                    login = userEntity.login,
                    avatarUrl = userEntity.avatarUrl,
                    htmlUrl = userEntity.htmlUrl
                )
            }
        }
    }
}