package com.ykphn.yapgitsin.domain.repository

import com.ykphn.yapgitsin.data.model.remote.products.CategoriesDTO
import com.ykphn.yapgitsin.data.model.remote.products.FoodDTO

interface FoodRepository {
    suspend fun getAllCategories(): Result<List<CategoriesDTO>>
    suspend fun getAllFoods(): Result<List<FoodDTO>>
}
