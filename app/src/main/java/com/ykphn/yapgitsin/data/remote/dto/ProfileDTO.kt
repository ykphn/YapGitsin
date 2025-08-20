package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProfileDTO(
    val id: String,
    val name: String?,
    val username: String,
    val bio: String?,
    val date: String,
    val likes: List<Int>,
    val stars: List<Int>
)