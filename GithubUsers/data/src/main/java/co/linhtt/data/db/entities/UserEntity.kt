package co.linhtt.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)