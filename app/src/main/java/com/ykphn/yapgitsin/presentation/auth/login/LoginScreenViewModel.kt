package com.ykphn.yapgitsin.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onLoginClick() {
        when {
            email.isBlank() -> errorMessage = "Mail adresi boş bırakılamaz!"
            password.isBlank() -> errorMessage = "Şifre alanı boş bırakılamaz!"
            else -> signIn(email, password)
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = AuthState.Loading
            authRepository.login(email, password)
                .onSuccess { _signInState.value = AuthState.Success }
                .onFailure { _signInState.value = AuthState.Error(it.message) }
        }
    }
}