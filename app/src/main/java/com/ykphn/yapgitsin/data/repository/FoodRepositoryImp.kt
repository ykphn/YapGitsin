package com.ykphn.yapgitsin.data.repository

import com.ykphn.yapgitsin.data.model.remote.products.CategoriesDTO
import com.ykphn.yapgitsin.data.model.remote.products.FoodDTO
import com.ykphn.yapgitsin.domain.repository.FoodRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImp @Inject constructor(
    private val supabaseClient: SupabaseClient
) : FoodRepository {
    private var cachedFoods: List<FoodDTO>? = null
    private var cachedCategories: List<CategoriesDTO>? = null

    override suspend fun getAllCategories(): Result<List<CategoriesDTO>> =
        withContext(Dispatchers.IO) {
            cachedCategories?.let { return@withContext Result.success(it) }
            try {
                val categories = supabaseClient.from("categories")
                    .select()
                    .decodeList<CategoriesDTO>()
                cachedCategories = categories
                Result.success(categories)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getAllFoods(): Result<List<FoodDTO>> = withContext(Dispatchers.IO) {
        cachedFoods?.let { return@withContext Result.success(it) }
        try {
            val foods = supabaseClient.from("foods")
                .select()
                .decodeList<FoodDTO>()
            cachedFoods = foods
            Result.success(foods)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
