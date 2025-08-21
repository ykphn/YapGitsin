package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val id: String,
    val name: String?,
    val username: String,
    val bio: String?,
    val date: String,
    val likes: List<Int>,
    val stars: List<Int>
)