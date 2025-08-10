package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.data.repository.AuthRepositoryImp
import com.ykphn.yapgitsin.data.repository.BucketsRepositoryImp
import com.ykphn.yapgitsin.data.repository.DatabaseRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused") // False Positive - Used by Hilt
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        impl: DatabaseRepositoryImp
    ): DatabaseRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImp
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBucketsRepository(
        impl: BucketsRepositoryImp
    ): BucketsRepository
}