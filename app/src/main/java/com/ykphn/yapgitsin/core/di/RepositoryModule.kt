package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.core.domain.repository.FoodRepository
import com.ykphn.yapgitsin.data.repository.AuthRepositoryImp
import com.ykphn.yapgitsin.data.repository.FoodRepositoryImp
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
        impl: FoodRepositoryImp
    ): FoodRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImp
    ): AuthRepository
}