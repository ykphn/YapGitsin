package com.ykphn.yapgitsin.presentation.main.models

data class UserProfile(
    val id: String,
    val fullName: String?,
    val username: String,
    val bio: String?,
    val joinedDate: String,
    val likes: List<Int>,
    val stars: List<Int>
)

