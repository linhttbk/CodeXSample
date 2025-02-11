package co.linhtt.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.linhtt.data.db.entities.RemoteKeyEntity
import co.linhtt.data.db.entities.UserEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKeys(keys: List<RemoteKeyEntity>)

    @Query("Select * from RemoteKeyEntity Where userLogin =:id")
    suspend fun getRemoteKeys(id:String):RemoteKeyEntity

    @Query("Delete From RemoteKeyEntity")
    suspend fun clearAll()
}