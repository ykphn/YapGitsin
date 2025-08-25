package com.ykphn.yapgitsin.presentation.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.core.domain.repository.MealRepository
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.main.models.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val _recipe = MutableStateFlow<Recipe?>(null)
    val recipe: StateFlow<Recipe?> = _recipe.asStateFlow()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    var starredMeals by mutableStateOf<List<Int>>(emptyList())
        private set
    var isStarred by mutableStateOf(false)
        private set

    fun loadInitialData(receiptId: String) {
        viewModelScope.launch {
            val meal = getMealsDetails(receiptId)
            val starred = getStarredMeals()

            if (meal && starred) {
                _uiState.value = UiState.Success
                isStarred = receiptId.toInt() in starredMeals
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    suspend fun getMealsDetails(mealId: String): Boolean =
        mealRepository.getMealById(mealId).onSuccess {
            _recipe.value = it.meals[0].toDomain()
        }.isSuccess


    suspend fun getStarredMeals(): Boolean =
        databaseRepository.getStarredMeals().onSuccess {
            starredMeals = it
        }.isSuccess


    fun addFavorite(id: Int) {
        viewModelScope.launch {
            if (starredMeals.contains(id)) {
                databaseRepository.unstarMeal(mealsId = starredMeals - id)
                    .onSuccess {
                        starredMeals = starredMeals - id
                        isStarred = false
                    }
            } else {
                databaseRepository.starMeal(mealsId = starredMeals + id)
                    .onSuccess {
                        starredMeals = starredMeals + id
                        isStarred = true
                    }
            }
        }
    }
}
