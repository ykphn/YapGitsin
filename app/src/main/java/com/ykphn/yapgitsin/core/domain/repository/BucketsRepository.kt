package com.ykphn.yapgitsin.core.domain.repository

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

interface BucketsRepository {
    suspend fun uploadUserAvatar(imageUri: Uri, context: Context): Result<Unit>
    suspend fun getUserAvatar(): Result<ImageBitmap>

}