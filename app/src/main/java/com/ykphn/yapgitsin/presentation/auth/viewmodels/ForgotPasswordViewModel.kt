package com.ykphn.yapgitsin.presentation.auth.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.core.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf(false)
        private set

    fun updateEmail(email: String) {
        this.email = email
    }

    fun resetPage() {
        email = ""
        errorMessage = false
        _uiState.value = UiState.Idle
    }

    fun forgotPassword() {
        if (email.isBlank()) errorMessage = true
        else
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                authRepository.forgotPassword(email).onSuccess {
                    _uiState.value = UiState.Success
                }.onFailure {
                    _uiState.value = UiState.Error
                }
            }
    }
}