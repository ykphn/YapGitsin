package com.ykphn.yapgitsin.core.domain.repository

import com.ykphn.yapgitsin.data.remote.dto.CategoriesResponse
import com.ykphn.yapgitsin.data.remote.dto.MealsResponse
import com.ykphn.yapgitsin.data.remote.dto.RecipesResponse

interface MealRepository {
    suspend fun getCategories(): Result<CategoriesResponse>
    suspend fun getMealsByCategory(category: String): Result<MealsResponse>
    suspend fun getMealById(id: String): Result<RecipesResponse>
}