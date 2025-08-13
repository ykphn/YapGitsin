package com.ykphn.yapgitsin.presentation.auth.state

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String? = null) : AuthState()
}
