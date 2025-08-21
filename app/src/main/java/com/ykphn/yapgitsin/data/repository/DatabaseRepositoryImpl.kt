package com.ykphn.yapgitsin.data.repository

import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.data.remote.dto.ProfileResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
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
        username: String,
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
                        "username" to username,
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
}
