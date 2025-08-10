package com.ykphn.yapgitsin.presentation.main.editprofile.state

sealed class SetupScreenUiState {
    object Idle : SetupScreenUiState()
    object Success : SetupScreenUiState()
    object AvatarError: SetupScreenUiState()
    object NameSurnameError: SetupScreenUiState()
    object NicknameError: SetupScreenUiState()
    object BiographyError: SetupScreenUiState()
}