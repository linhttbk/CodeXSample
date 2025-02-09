package co.linhtt.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import co.linhtt.data.db.UserDatabase
import co.linhtt.data.db.entities.QueryEntity
import co.linhtt.data.db.entities.RemoteKeyEntity
import co.linhtt.data.db.entities.UserEntity
import javax.inject.Inject

// paging3 library - load data from remote then save to local database
@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator @Inject constructor(
    private val githubUserService: GithubUserService,
    private val userDatabase: UserDatabase
) : RemoteMediator<Int, UserEntity>() {
    companion object {
        private const val GITHUB_STARTING_PAGE_INDEX = 1
        const val ITEMS_PER_PAGE = 20
        private const val USER_QUERY_CACHE_EXPIRED_TIME = 5 * 60 * 1000 // 5 minutes
    }

    override suspend fun initialize(): InitializeAction {
        val shouldFetchFirstPage = shouldFetchFromNetworkForPage(GITHUB_STARTING_PAGE_INDEX)
        return if (shouldFetchFirstPage) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            // skip as we can use cache
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    private suspend fun shouldFetchFromNetworkForPage(githubStartingPageIndex: Int): Boolean {
        val query = createUserListQueryKey(githubStartingPageIndex)
        val queryEntity = userDatabase.queryDao().getByQuery(query)
        return queryEntity == null || System.currentTimeMillis() - queryEntity.lastUpdatedAt > USER_QUERY_CACHE_EXPIRED_TIME
    }

    private fun createUserListQueryKey(page: Int): String {
        return "users/since=${page * ITEMS_PER_PAGE}&page=$page"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> GITHUB_STARTING_PAGE_INDEX
            //don't need to handle prepend because we only load the first page on refresh
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val key = getRemoteKeyForLastItem(state)
                val nextKey = key?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = key != null)
                nextKey
            }
        }
        val shouldFetchThisPage = shouldFetchFromNetworkForPage(page)
        if (shouldFetchThisPage.not()) {
            return MediatorResult.Success(endOfPaginationReached = false)
        } else {
            try {
                val users = githubUserService.getUsers(page * ITEMS_PER_PAGE, page)
                val endOfPaginationReached = users.isEmpty()
                userDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        userDatabase.userDao().clearAll()
                        userDatabase.remoteKeyDao().clearAll()
                    }
                    val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val userEntities = mutableListOf<UserEntity>()
                    val keys = users.map {
                        userEntities.add(it.toEntity())
                        RemoteKeyEntity(
                            it.login,
                            prevKey, nextKey
                        )
                    }
                    userDatabase.remoteKeyDao().saveKeys(keys)
                    userDatabase.userDao().saveUsers(userEntities)
                    userDatabase.queryDao().saveQuery(
                        QueryEntity(
                            createUserListQueryKey(page),
                            System.currentTimeMillis()
                        )
                    )
                }
                return MediatorResult.Success(endOfPaginationReached)
            } catch (ex: Exception) {
                ex.printStackTrace()
                return MediatorResult.Error(ex)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items
        // From the last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                userDatabase.remoteKeyDao().getRemoteKeys(it.login)
            }
    }

}