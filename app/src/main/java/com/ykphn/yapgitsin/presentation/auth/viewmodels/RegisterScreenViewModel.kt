package com.ykphn.yapgitsin.presentation.auth.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.presentation.auth.state.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
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

    fun onLoginClick() {
        if (password != passwordRepeat) errorMessage = "Şifreler uyuşmuyor!"
        else
            when {
                username.isBlank() -> errorMessage = "Kullanıcı adı boş bırakılamaz!"
                email.isBlank() -> errorMessage = "Mail adresi boş bırakılamaz!"
                password.isBlank() -> errorMessage = "Şifre alanı boş bırakılamaz!"
                passwordRepeat.isBlank() -> errorMessage = "Şifre alanı boş bırakılamaz!"
                else -> registerUserAccount(username, email, password)
            }
    }

    // Function to handle user registration
    private fun registerUserAccount(username: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = AuthState.Loading
            authRepository.register(
                email = email,
                password = password
            ).onSuccess {
                _signUpState.value = AuthState.Success
                databaseRepository.createProfile(username)
                    .onSuccess {
                        _signUpState.value = AuthState.Success
                    }
            }.onFailure { exception ->
                val errorMessage = exception.message.orEmpty().lowercase()
                when {
                    "email" in errorMessage -> _signUpState.value = AuthState.Error("Invalid MAIL!")
                    "password" in errorMessage -> _signUpState.value =
                        AuthState.Error("Invalid PASSWORD!")

                    else -> _signUpState.value = AuthState.Error("General ERROR")
                }
            }
        }
    }
}