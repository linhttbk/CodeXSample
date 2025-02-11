package co.linhtt.data.di

import co.linhtt.data.repository.UserProvider
import co.linhtt.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideUserRepository(repository: UserProvider): UserRepository
}