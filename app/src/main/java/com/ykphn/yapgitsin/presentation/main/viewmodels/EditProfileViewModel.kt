package com.ykphn.yapgitsin.presentation.main.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.core.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val bucketsRepository: BucketsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    var name by  mutableStateOf("")
        private set
    var username by  mutableStateOf("")
        private set
    var bio by  mutableStateOf("")
        private set
    var uri by  mutableStateOf<Uri?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateName(newName: String) {
        name = newName
        errorMessage = null
    }

    fun updateUsername(newUsername: String) {
        username = newUsername
        errorMessage = null
    }

    fun updateBio(newBio: String) {
        bio = newBio
        errorMessage = null
    }

    fun updateUri(newUri: Uri) {
        uri = newUri
        errorMessage = null
    }

    fun onUpdateClick(context: Context) {
        when {
            uri == null -> errorMessage = "Lütfen bir resim seçiniz"
            name.isBlank() -> errorMessage = "İsim ve soyisim alanı boş bırakılamaz"
            username.isBlank() -> errorMessage = "Kullanıcı adı boş bırakılamaz"
            bio.isBlank() -> errorMessage = "Biyografi alanı boş bırakılamaz"
            else -> updateProfile(context)
        }
    }

    fun updateProfile(context: Context) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val profileUpdateResult = updateProfileField(name, username, bio)
            val avatarUpdateResult = updateProfileAvatar(context, uri!!)

            if (profileUpdateResult && avatarUpdateResult)
                _uiState.value = UiState.Success
            else
                _uiState.value = UiState.Error

        }
    }

    private suspend fun updateProfileField(name: String, username: String, bio: String): Boolean =
        databaseRepository.updateProfile(name, username, bio).onFailure {
            Log.e("EditProfileViewModel", "updateProfileField: $it")
        }.isSuccess


    private suspend fun updateProfileAvatar(context: Context, uri: Uri): Boolean =
        bucketsRepository.uploadUserAvatar(uri, context).onFailure {
            Log.e("EditProfileViewModel", "updateProfileField: $it")
        }.isSuccess

}