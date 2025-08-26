package com.ykphn.yapgitsin.presentation.auth.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.presentation.auth.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // StateFlow for sign-up state
    private val _signUpState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signUpState: StateFlow<AuthState> = _signUpState.asStateFlow()

    // UI state and basic field validation logic for the register form
    var username by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordRepeat by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Functions to update UI state and validate fields
    fun updateUsername(newUsername: String) {
        username = newUsername
        errorMessage = null
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun updateRepeatPassword(newPassword: String) {
        passwordRepeat = newPassword
        errorMessage = null
    }

    fun onLoginClick(context: Context) {
        if (password != passwordRepeat) errorMessage = context.getString(R.string.error_password_mismatch)
        else
            when {
                username.isBlank() -> errorMessage = context.getString(R.string.error_username_empty)
                email.isBlank() -> errorMessage = context.getString(R.string.error_email_empty)
                password.isBlank() -> errorMessage = context.getString(R.string.error_password_empty)
                passwordRepeat.isBlank() -> errorMessage = context.getString(R.string.error_password_empty)
                else -> registerUserAccount(context)
            }
    }

    // Function to handle user registration
    private fun registerUserAccount(context: Context) {
        viewModelScope.launch {
            _signUpState.value = AuthState.Loading
            authRepository.register(
                username = username,
                email = email,
                password = password
            ).onSuccess {
                _signUpState.value = AuthState.Success
            }.onFailure { exception ->
                val errorMessage = exception.message.orEmpty().lowercase()
                when {
                    "email" in errorMessage -> _signUpState.value = AuthState.Error(context.getString(R.string.error_invalid_email))
                    "password" in errorMessage -> _signUpState.value =
                        AuthState.Error(context.getString(R.string.error_invalid_password))
                    else -> _signUpState.value = AuthState.Error(context.getString(R.string.error_general))
                }
            }
        }
    }
}