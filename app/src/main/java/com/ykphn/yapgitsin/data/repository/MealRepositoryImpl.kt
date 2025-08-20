package com.ykphn.yapgitsin.data.repository

import com.ykphn.yapgitsin.core.domain.repository.MealRepository
import com.ykphn.yapgitsin.data.remote.api.MealApiClient
import com.ykphn.yapgitsin.data.remote.dto.CategoriesResponse
import com.ykphn.yapgitsin.data.remote.dto.MealsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepositoryImpl @Inject constructor(
    private val api: MealApiClient
) : MealRepository {

    private var cachedCategories: CategoriesResponse? = null
    override suspend fun getCategories(): Result<CategoriesResponse> = withContext(Dispatchers.IO) {
        cachedCategories?.let { return@withContext Result.success(it) }
        return@withContext try {
            val response = api.getCategories()
            cachedCategories = response
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private val cachedMealsByCategory: MutableMap<String, MealsResponse> = mutableMapOf()
    override suspend fun getMealsByCategory(category: String): Result<MealsResponse> = withContext(Dispatchers.IO) {
        cachedMealsByCategory[category]?.let { cached ->
            return@withContext Result.success(cached)
        }
        return@withContext try {
            val response = api.getFoodsByCategory(category)
            cachedMealsByCategory[category] = response
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private val cachedMealsById: MutableMap<String, MealsResponse> = mutableMapOf()
    override suspend fun getMealById(id: String): Result<MealsResponse> = withContext(Dispatchers.IO) {
        cachedMealsById[id]?.let { cached ->
            return@withContext Result.success(cached)
        }
        return@withContext try {
            val response = api.getMealById(id)
            cachedMealsById[id] = response
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}