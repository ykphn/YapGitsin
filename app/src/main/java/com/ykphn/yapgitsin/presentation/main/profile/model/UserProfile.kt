package com.ykphn.yapgitsin.presentation.main.profile.model

import androidx.compose.ui.graphics.ImageBitmap

data class UserProfile(
    val id: String,
    val avatar: ImageBitmap?,
    val fullName: String?,
    val username: String,
    val bio: String?,
    val date: String,
    val likes: Int?,
    val stars: Int?
)

