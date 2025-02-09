package co.linhtt.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QueryEntity(
   @PrimaryKey val query:String,
    val lastUpdatedAt:Long
)
