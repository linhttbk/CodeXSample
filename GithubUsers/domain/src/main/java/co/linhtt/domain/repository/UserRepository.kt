package co.linhtt.domain.repository

import androidx.paging.PagingData
import co.linhtt.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers():Flow<PagingData<GithubUser>>
}