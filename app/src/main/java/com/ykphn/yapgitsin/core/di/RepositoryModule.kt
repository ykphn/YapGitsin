package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.data.repository.AuthRepositoryImpl
import com.ykphn.yapgitsin.data.repository.FoodRepositoryImp
import com.ykphn.yapgitsin.domain.repository.AuthRepository
import com.ykphn.yapgitsin.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
        impl: AuthRepositoryImpl
    ): AuthRepository
}