package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.BuildConfig
import com.ykphn.yapgitsin.core.domain.repository.MealRepository
import com.ykphn.yapgitsin.data.remote.api.MealApiClient
import com.ykphn.yapgitsin.data.remote.client.KtorHttpClient
import com.ykphn.yapgitsin.data.repository.MealRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val SUPABASE_BASE_URL = BuildConfig.SUPABASE_BASE_URL
    private const val SUPABASE_API_KEY = BuildConfig.SUPABASE_API_KEY

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_BASE_URL, supabaseKey = SUPABASE_API_KEY
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient =
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

    @Provides
    @Singleton
    fun provideKtorHttpClient(): KtorHttpClient = KtorHttpClient()

    @Provides
    @Singleton
    fun provideMealApiClient(ktorHttpClient: KtorHttpClient): MealApiClient =
        MealApiClient(ktorHttpClient.httpClient)

}