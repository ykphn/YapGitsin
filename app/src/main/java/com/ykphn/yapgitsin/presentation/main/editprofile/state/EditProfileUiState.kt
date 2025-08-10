package com.ykphn.yapgitsin.presentation.main.editprofile.state

sealed class EditProfileUiState {
    object Idle : EditProfileUiState()
    object Loading : EditProfileUiState()
    object Success : EditProfileUiState()
    object Error : EditProfileUiState()
}
