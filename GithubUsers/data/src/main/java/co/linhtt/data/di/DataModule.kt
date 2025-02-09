package co.linhtt.data.di

import android.app.Application
import androidx.room.Room
import co.linhtt.data.db.UserDatabase
import co.linhtt.data.remote.GithubUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    fun provideUserDatabase(context:Application):UserDatabase{
        return Room.databaseBuilder(context,UserDatabase::class.java,"users.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideGithubUserService():GithubUserService{
        return GithubUserService.create()
    }
}