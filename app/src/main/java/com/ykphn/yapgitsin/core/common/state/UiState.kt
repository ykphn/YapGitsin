package com.ykphn.yapgitsin.core.common.state

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    object Error : UiState()
}