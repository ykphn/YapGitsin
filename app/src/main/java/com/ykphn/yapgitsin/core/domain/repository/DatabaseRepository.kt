package com.ykphn.yapgitsin.core.domain.repository

import com.ykphn.yapgitsin.data.remote.dto.ProfileResponse

interface DatabaseRepository {
    suspend fun getProfile(): Result<ProfileResponse>
    suspend fun updateProfile(name: String, bio: String): Result<Unit>
    suspend fun getStarredMeals(): Result<List<Int>>
    suspend fun starMeal(mealsId: List<Int>): Result<Unit>
    suspend fun unstarMeal(mealsId: List<Int>): Result<Unit>
}
