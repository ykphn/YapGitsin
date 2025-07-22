package com.ykphn.yapgitsin.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL = "https://syyaatmfgeozhxvfkewl.supabase.co"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN5eWFhdG1mZ2Vvemh4dmZrZXdsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTI0NzU4NzUsImV4cCI6MjA2ODA1MTg3NX0.MHmiTRc-fM7d1ab7PQyg9QZLiYg6gJ_DT6U1CU-My4E"

    private val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor(API_KEY)).build()

    val api: SupabaseApi by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(SupabaseApi::class.java)
    }
}
