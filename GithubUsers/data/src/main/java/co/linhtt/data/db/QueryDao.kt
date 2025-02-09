package co.linhtt.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.linhtt.data.db.entities.QueryEntity

@Dao
interface QueryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuery(queryEntity: QueryEntity)

    @Query("SELECT * FROM QueryEntity WHERE `query` =:query")
    suspend fun getByQuery(query:String):QueryEntity?

    @Query("Delete from QueryEntity")
    suspend fun clearAll()
}