package com.ykphn.yapgitsin.core.domain.repository

import com.ykphn.yapgitsin.data.remote.dto.ProfileResponse

interface DatabaseRepository {
    suspend fun getProfile(): Result<ProfileResponse>
    suspend fun updateProfile(name: String, username: String, bio: String): Result<Unit>
}
