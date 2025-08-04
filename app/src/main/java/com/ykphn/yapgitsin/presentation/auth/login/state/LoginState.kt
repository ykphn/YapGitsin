package com.ykphn.yapgitsin.presentation.auth.login.state

sealed class LoginState {
    object Idle : LoginState()
    object Success: LoginState()
    object Loading : LoginState()
    object GeneralError : LoginState()

}