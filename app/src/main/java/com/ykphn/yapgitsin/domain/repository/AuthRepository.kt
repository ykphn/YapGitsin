package com.ykphn.yapgitsin.domain.repository

interface AuthRepository {
    suspend fun register(username: String, email: String, password: String): Result<Unit>
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun logout(): Result<Unit>
}
