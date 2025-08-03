package com.ykphn.yapgitsin.presentation.auth.register.state

sealed class RegisterFormState {
    object Idle : RegisterFormState()
    object Success: RegisterFormState()
    object UsernameError : RegisterFormState()
    object EmailError : RegisterFormState()
    object PasswordError : RegisterFormState()
    object PasswordMatchError : RegisterFormState()
}