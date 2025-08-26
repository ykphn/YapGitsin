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
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // StateFlow for sign-in
    private val _signInState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signInState: StateFlow<AuthState> = _signInState.asStateFlow()

    // UI state and basic field validation logic for the login form
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun updateEmail(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun onLoginClick(context: Context) {
        when {
            email.isBlank() -> errorMessage = context.getString(R.string.error_email_empty)
            password.isBlank() -> errorMessage = context.getString(R.string.error_password_empty)
            else -> signIn()
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            _signInState.value = AuthState.Loading
            authRepository.login(email, password)
                .onSuccess { _signInState.value = AuthState.Success }
                .onFailure { _signInState.value = AuthState.Error(it.message) }
        }
    }
}