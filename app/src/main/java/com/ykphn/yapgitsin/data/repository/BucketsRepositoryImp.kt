package com.ykphn.yapgitsin.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.utils.uriToImageBitmap
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BucketsRepositoryImp @Inject constructor(
    private val supabaseClient: SupabaseClient
) : BucketsRepository {

    override suspend fun uploadUserAvatar(
        imageUri: Uri, context: Context
    ) = withContext(Dispatchers.IO) {
        val byteArray = context.contentResolver.openInputStream(imageUri)?.readBytes()
            ?: return@withContext Result.failure(
                exception = IllegalStateException("Resim okunamadı.")
            )
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )
        val path = "${user.id}.png"
        try {
            supabaseClient.storage.from("avatars").upload(
                path = path, data = byteArray
            ) { upsert = true }
            tempImage = uriToImageBitmap(context, imageUri)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private var tempImage: ImageBitmap? = null
    override suspend fun getUserAvatar(): Result<ImageBitmap> = withContext(Dispatchers.IO) {
        tempImage?.let { return@withContext Result.success(it) }
        val user = supabaseClient.auth.currentUserOrNull() ?: return@withContext Result.failure(
            exception = IllegalStateException("Kullanıcı oturumu yok.")
        )

        val path = "${user.id}.png"
        try {
            val byteArray = supabaseClient.storage.from("avatars").downloadPublic(path)

            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            val imageBitmap = bitmap.asImageBitmap()
            tempImage = imageBitmap
            return@withContext Result.success(imageBitmap)
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }


}