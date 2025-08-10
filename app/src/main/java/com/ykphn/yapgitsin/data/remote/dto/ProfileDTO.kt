package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    val id: String,
    val name: String?,
    val username: String,
    val bio: String?,
    val date: String,
    val likes: Int?,
    val stars: Int?
)