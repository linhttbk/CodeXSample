package co.linhtt.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.linhtt.data.db.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(users: List<UserEntity>)

    // we want to design local first solution so we always load data from local database
    @Query("SELECT * FROM UserEntity")
    fun getUsers():PagingSource<Int,UserEntity>

    @Query("Delete From UserEntity")
    suspend fun clearAll()
}