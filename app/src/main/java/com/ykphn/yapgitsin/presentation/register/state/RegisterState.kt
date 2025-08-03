package com.ykphn.yapgitsin.presentation.register.state

sealed class RegisterState {
    object Idle : RegisterState()
    object Success: RegisterState()
    object Loading : RegisterState()
    object EmailError : RegisterState()
    object PasswordError : RegisterState()
    object GeneralError : RegisterState()

}