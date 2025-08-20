package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.core.domain.repository.MealRepository
import com.ykphn.yapgitsin.data.repository.AuthRepositoryImpl
import com.ykphn.yapgitsin.data.repository.BucketsRepositoryImpl
import com.ykphn.yapgitsin.data.repository.DatabaseRepositoryImpl
import com.ykphn.yapgitsin.data.repository.MealRepositoryImpl
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
        impl: DatabaseRepositoryImpl
    ): DatabaseRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBucketsRepository(
        impl: BucketsRepositoryImpl
    ): BucketsRepository

    @Binds
    @Singleton
    abstract fun bindMealRepository(
        impl: MealRepositoryImpl
    ): MealRepository
}