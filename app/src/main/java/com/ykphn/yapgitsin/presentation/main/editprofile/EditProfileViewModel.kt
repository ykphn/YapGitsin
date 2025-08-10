package com.ykphn.yapgitsin.presentation.main.editprofile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.presentation.main.editprofile.state.EditProfileUiState
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
    private val _uiState = MutableStateFlow<EditProfileUiState>(EditProfileUiState.Idle)
    val uiState: StateFlow<EditProfileUiState> = _uiState

    fun updateProfile(context: Context, uri: Uri, name: String, username: String, bio: String) {
        viewModelScope.launch {
            _uiState.value = EditProfileUiState.Loading
            val profileUpdateResult = updateProfileField(name, username, bio)
            val avatarUpdateResult = updateProfileAvatar(context, uri)

            if (profileUpdateResult && avatarUpdateResult)
                _uiState.value = EditProfileUiState.Success
            else
                _uiState.value = EditProfileUiState.Error

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