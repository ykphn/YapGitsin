package com.ykphn.yapgitsin.presentation.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.presentation.auth.register.state.RegisterState
import com.ykphn.yapgitsin.core.domain.repository.AuthRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
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
    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState.asStateFlow()
    fun registerUserAccount(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            authRepository.register(
                email = email,
                password = password
            ).onSuccess {
                databaseRepository.createProfile(username).onSuccess {
                    Log.d("RegisterScreenViewModel", "Profile created successfully")
                }.onFailure { it ->
                    Log.d("RegisterScreenViewModel", "Profile creation failed $it")
                }
                _registerState.value = RegisterState.Success
            } .onFailure { exception ->
                val errorMessage = exception.message.orEmpty().lowercase()
                when {
                    "email" in errorMessage -> _registerState.value = RegisterState.EmailError
                    "password" in errorMessage -> _registerState.value = RegisterState.PasswordError
                    else -> _registerState.value = RegisterState.GeneralError
                }
            }
        }
    }
}