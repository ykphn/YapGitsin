package com.ykphn.yapgitsin.presentation.main.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.core.state.UiState
import com.ykphn.yapgitsin.presentation.main.models.EditProfileStep
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

    var step by mutableStateOf(EditProfileStep.PHOTO)
        private set
    var name by mutableStateOf("")
        private set
    var bio by mutableStateOf("")
        private set
    var uri by mutableStateOf<Uri?>(null)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onBackClick() {
        step = EditProfileStep.PHOTO
        errorMessage = null
    }

    fun updateName(newName: String) {
        name = newName
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

    fun updateProfileAvatar(context: Context) {
        uri?.let {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                bucketsRepository.uploadUserAvatar(it, context).onSuccess {
                    _uiState.value = UiState.Idle
                    step = EditProfileStep.INFO
                }.onFailure {
                    _uiState.value = UiState.Idle
                    errorMessage = context.getString(R.string.error_avatar_upload)
                }

            }
        } ?: run {
            errorMessage = context.getString(R.string.error_select_image)
        }
    }


    fun updateProfileField(context: Context) {
        when {
            name.isBlank() -> errorMessage = context.getString(R.string.error_name_empty)
            bio.isBlank() -> errorMessage = context.getString(R.string.error_bio_empty)
            else -> {
                viewModelScope.launch {
                    _uiState.value = UiState.Loading
                    databaseRepository.updateProfile(name, bio).onSuccess {
                        _uiState.value = UiState.Success
                    }.onFailure {
                        _uiState.value = UiState.Idle
                        errorMessage = context.getString(R.string.error_profile_update)
                    }
                }
            }
        }
    }
}