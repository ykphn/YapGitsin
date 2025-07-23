package com.ykphn.yapgitsin.core.network


import com.ykphn.yapgitsin.data.model.CategoriesDTO
import com.ykphn.yapgitsin.data.model.FoodDTO
import retrofit2.http.GET

interface SupabaseApi {
    @GET("rest/v1/categories")
    suspend fun getCategories(): List<CategoriesDTO>
    @GET("rest/v1/foods")
    suspend fun getFoods(): List<FoodDTO>
}
