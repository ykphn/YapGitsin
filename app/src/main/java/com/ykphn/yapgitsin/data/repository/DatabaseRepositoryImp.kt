package com.ykphn.yapgitsin.data.repository

import com.ykphn.yapgitsin.data.remote.dto.CategoriesDTO
import com.ykphn.yapgitsin.data.remote.dto.FoodDTO
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.core.domain.utils.formatToMonthYear
import com.ykphn.yapgitsin.data.remote.dto.ProfileDTO
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

@Singleton
class DatabaseRepositoryImp @Inject constructor(
    private val supabaseClient: SupabaseClient
) : DatabaseRepository {

    private var cachedCategories: List<CategoriesDTO>? = null
    override suspend fun getCategories(): Result<List<CategoriesDTO>> =
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

    private var cachedFoods: List<FoodDTO>? = null
    override suspend fun getFoods(): Result<List<FoodDTO>> = withContext(Dispatchers.IO) {
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

    @OptIn(ExperimentalTime::class)
    override suspend fun createProfile(name: String): Result<Unit> = withContext(Dispatchers.IO) {
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        val userId = user.id
        val date = formatToMonthYear(rawDate = user.createdAt)
        return@withContext try {
            supabaseClient
                .from("profiles")
                .insert(
                    mapOf(
                        "id" to userId,
                        "username" to name,
                        "date" to date
                    )
                )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(
        name: String,
        username: String,
        bio: String
    ): Result<Unit> {
        val userId = supabaseClient.auth.currentUserOrNull()!!.id

        return try {
            supabaseClient
                .from("profiles")
                .update(
                    mapOf(
                        "name" to name,
                        "username" to username,
                        "bio" to bio
                    )
                ) {
                    filter {
                        eq("id", userId)
                    }
                }
            cachedProfile = null
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private var cachedProfile: ProfileDTO? = null
    override suspend fun getProfile(): Result<ProfileDTO> = withContext(Dispatchers.IO) {
        cachedProfile?.let { return@withContext Result.success(it) }
        val userId = supabaseClient.auth.currentUserOrNull()!!.id
        try {
            val response = supabaseClient
                .from("profiles")
                .select {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeList<ProfileDTO>()
            val profile = response.firstOrNull()
                ?: return@withContext Result.failure(Exception("Profil bulunamadı -> $userId"))
            cachedProfile = profile
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
