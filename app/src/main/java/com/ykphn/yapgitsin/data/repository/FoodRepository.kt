package com.ykphn.yapgitsin.data.repository

import com.ykphn.yapgitsin.core.network.SupabaseApi
import com.ykphn.yapgitsin.data.model.CategoriesDTO
import com.ykphn.yapgitsin.data.model.FoodDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(
    private val api: SupabaseApi
) {
    private var cachedFoods: List<FoodDTO>? = null
    private var cachedCategories: List<CategoriesDTO>? = null

    suspend fun getAllCategories(): Result<List<CategoriesDTO>> {
        return withContext(Dispatchers.IO) {
            cachedCategories?.let {
                return@withContext Result.success(it)
            }
            try {
                val categories = api.getCategories()
                cachedCategories = categories
                Result.success(categories)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: IOException) {
                Result.failure(e)
            }

        }
    }

    suspend fun getAllFoods(): Result<List<FoodDTO>> {
        return withContext(Dispatchers.IO) {
            cachedFoods?.let {
                return@withContext Result.success(it)
            }
            try {
                val foods = api.getFoods()
                cachedFoods = foods
                Result.success(foods)
            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: IOException) {
                Result.failure(e)
            }
        }
    }
}

