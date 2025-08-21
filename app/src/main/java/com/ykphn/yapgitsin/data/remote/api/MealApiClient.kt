package com.ykphn.yapgitsin.data.remote.api

import com.ykphn.yapgitsin.BuildConfig
import com.ykphn.yapgitsin.data.remote.dto.CategoriesResponse
import com.ykphn.yapgitsin.data.remote.dto.MealsResponse
import com.ykphn.yapgitsin.data.remote.dto.RecipesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MealApiClient(
    private val httpClient: HttpClient
) {
    private val baseUrl = BuildConfig.MEAL_API_URL

    suspend fun getCategories(): CategoriesResponse =
        httpClient.get("${baseUrl}categories.php").body()

    suspend fun getFoodsByCategory(category: String): MealsResponse =
        httpClient.get("${baseUrl}filter.php") {
            url { parameters.append("c", category) }
        }.body()

    suspend fun getMealById(id: String): RecipesResponse =
        httpClient.get("${baseUrl}lookup.php") {
            url { parameters.append("i", id) }
        }.body()
}
