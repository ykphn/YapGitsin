    package com.ykphn.yapgitsin.core.state

    sealed class UiState {
        object Idle: UiState()
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
    }