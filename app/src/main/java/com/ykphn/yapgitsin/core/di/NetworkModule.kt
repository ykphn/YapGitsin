package com.ykphn.yapgitsin.core.di

import com.ykphn.yapgitsin.core.network.AuthInterceptor
import com.ykphn.yapgitsin.core.network.SupabaseApi
import com.ykphn.yapgitsin.data.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://syyaatmfgeozhxvfkewl.supabase.co/rest/v1/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN5eWFhdG1mZ2Vvemh4dmZrZXdsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTI0NzU4NzUsImV4cCI6MjA2ODA1MTg3NX0.MHmiTRc-fM7d1ab7PQyg9QZLiYg6gJ_DT6U1CU-My4E"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(API_KEY))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSupabaseApi(retrofit: Retrofit): SupabaseApi =
        retrofit.create(SupabaseApi::class.java)

    @Provides
    @Singleton
    fun provideFoodRepository(api: SupabaseApi): FoodRepository =
        FoodRepository(api)


}