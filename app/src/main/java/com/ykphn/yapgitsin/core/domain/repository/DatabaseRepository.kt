package com.ykphn.yapgitsin.core.domain.repository

import com.ykphn.yapgitsin.data.remote.dto.CategoriesDTO
import com.ykphn.yapgitsin.data.remote.dto.FoodDTO
import com.ykphn.yapgitsin.data.remote.dto.ProfileDTO

interface DatabaseRepository {
    suspend fun getCategories(): Result<List<CategoriesDTO>>
    suspend fun getFoods(): Result<List<FoodDTO>>
    suspend fun createProfile(name: String): Result<Unit>
    suspend fun updateProfile(name: String, username: String, bio: String): Result<Unit>
    suspend fun getProfile(): Result<ProfileDTO>
}
