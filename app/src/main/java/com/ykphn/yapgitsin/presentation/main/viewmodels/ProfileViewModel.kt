package com.ykphn.yapgitsin.presentation.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.domain.repository.BucketsRepository
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.core.domain.repository.MealRepository
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.main.models.Meal
import com.ykphn.yapgitsin.presentation.main.models.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val bucketsRepository: BucketsRepository,
    private val mealRepository: MealRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val _userAvatar = MutableStateFlow<ImageBitmap?>(null)
    val userAvatar: StateFlow<ImageBitmap?> = _userAvatar.asStateFlow()
    private val _profileData = MutableStateFlow<UserProfile?>(null)
    val profileData: StateFlow<UserProfile?> = _profileData.asStateFlow()
    var favorites by mutableStateOf<List<Meal>>(emptyList())
        private set

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val profileResult = getUserProfile()
            getUserAvatar()
            getFavoriteFoods()

            if (profileResult) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    private suspend fun getUserAvatar() {
        bucketsRepository.getUserAvatar()
            .onSuccess {
                _userAvatar.value = null
                _userAvatar.value = it
            }
    }

    private suspend fun getUserProfile(): Boolean {
        return databaseRepository.getProfile().onSuccess { profile ->
            _profileData.value = UserProfile(
                id = profile.id,
                fullName = profile.name,
                username = profile.username,
                bio = profile.bio,
                joinedDate = profile.createdAt,
                stars = profile.stars
            )
        }.isSuccess
    }

    private suspend fun getFavoriteFoods(): Boolean {
        return try {
            val allFavorites = mutableListOf<Meal>()
            _profileData.value?.stars?.forEach { mealId ->
                val result = mealRepository.getMealById(mealId.toString())
                result.onSuccess { data ->
                    val mappedMeals = data.meals.map { dto ->
                        Meal(
                            id = dto.idMeal,
                            categoryId = dto.strCategory ?: "",
                            name = dto.strMeal,
                            imageUrl = dto.strMealThumb ?: ""
                        )
                    }
                    allFavorites.addAll(mappedMeals)
                    favorites = allFavorites.toList()
                }
            }

            true
        } catch (_: Exception) {
            false
        }
    }

}