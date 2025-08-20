package com.ykphn.yapgitsin.data.repository

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
class DatabaseRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
) : DatabaseRepository {

    @OptIn(ExperimentalTime::class)
    override suspend fun createProfile(name: String): Result<Unit> = withContext(Dispatchers.IO) {
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        val date = formatToMonthYear(rawDate = user.createdAt)
        return@withContext try {
            supabaseClient
                .from("profiles")
                .insert(
                    mapOf(
                        "id" to user.id,
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

    private var cachedProfile: ProfileDTO? = null
    override suspend fun getProfile(): Result<ProfileDTO> = withContext(Dispatchers.IO) {
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
                .decodeList<ProfileDTO>()
            val profile = response.firstOrNull()
                ?: return@withContext Result.failure(Exception("Profil bulunamadı -> ${user.id}"))
            cachedProfile = profile
            Result.success(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
