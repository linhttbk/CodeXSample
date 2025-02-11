package co.linhtt.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import co.linhtt.data.db.entities.QueryEntity
import co.linhtt.data.db.entities.RemoteKeyEntity
import co.linhtt.data.db.entities.UserEntity

@Database(entities = [UserEntity::class, QueryEntity::class,RemoteKeyEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun queryDao(): QueryDao
    abstract fun remoteKeyDao():RemoteKeyDao
}