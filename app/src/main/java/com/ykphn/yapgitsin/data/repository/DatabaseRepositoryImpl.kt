package com.ykphn.yapgitsin.data.repository

import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.data.remote.dto.ProfileResponse
import com.ykphn.yapgitsin.data.remote.dto.StarsResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : DatabaseRepository {

    private var cachedProfile: ProfileResponse? = null
    override suspend fun getProfile(): Result<ProfileResponse> = withContext(Dispatchers.IO) {
        cachedProfile?.let { return@withContext Result.success(it) }
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        try {
            val response = supabaseClient
                .from("profiles")
                .select {
                    filter {
                        eq("id", user.id)
                    }
                }
                .decodeList<ProfileResponse>()
            val profile = response.firstOrNull()
                ?: return@withContext Result.failure(Exception("Profil bulunamadı -> ${user.id}"))
            cachedProfile = profile
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(
        name: String,
        bio: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        return@withContext try {
            supabaseClient
                .from("profiles")
                .update(
                    mapOf(
                        "name" to name,
                        "bio" to bio
                    )
                ) {
                    filter {
                        eq("id", user.id)
                    }
                }
            cachedProfile = null
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStarredMeals(): Result<List<Int>> = withContext(Dispatchers.IO) {
        cachedProfile?.let { return@withContext Result.success(it.stars) }
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        try {
            val response = supabaseClient
                .from("profiles")
                .select(Columns.list("stars")) {
                    filter { eq("id", user.id) }
                }
                .decodeList<StarsResponse>()
            cachedProfile = cachedProfile?.copy(stars = response[0].stars)
            Result.success(response[0].stars)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun starMeal(mealsId: List<Int>): Result<Unit> = withContext(Dispatchers.IO) {
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        return@withContext try {
            supabaseClient
                .from("profiles")
                .update(
                    mapOf(
                        "stars" to mealsId,
                    )
                ) {
                    filter {
                        eq("id", user.id)
                    }
                }
            cachedProfile = cachedProfile?.copy(stars = mealsId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unstarMeal(mealsId: List<Int>): Result<Unit> =
        withContext(Dispatchers.IO) {
            val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
                IllegalStateException("Kullanıcı oturumu yok.")
            )
            return@withContext try {
                supabaseClient
                    .from("profiles")
                    .update(
                        mapOf("stars" to mealsId)
                    ) {
                        filter { eq("id", user.id) }
                    }
                cachedProfile = cachedProfile?.copy(stars = mealsId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

}
