package com.ykphn.yapgitsin.core.domain.repository

import com.ykphn.yapgitsin.data.remote.dto.CategoriesDTO
import com.ykphn.yapgitsin.data.remote.dto.FoodDTO

interface FoodRepository {
    suspend fun getAllCategories(): Result<List<CategoriesDTO>>
    suspend fun getAllFoods(): Result<List<FoodDTO>>
}
