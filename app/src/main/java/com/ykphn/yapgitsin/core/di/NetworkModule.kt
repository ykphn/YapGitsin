package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = BuildConfig.SUPABASE_BASE_URL
    private const val API_KEY = BuildConfig.SUPABASE_API_KEY

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient = createSupabaseClient(
        supabaseUrl = BASE_URL, supabaseKey = API_KEY
    ) {
        install(Postgrest)
        install(Auth)
    }
}