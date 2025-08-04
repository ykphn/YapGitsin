package com.ykphn.yapgitsin.presentation.auth.login.state

sealed class LoginFormState {
    object Idle : LoginFormState()
    object Success: LoginFormState()
    object EmailError : LoginFormState()
    object PasswordError : LoginFormState()
}