package com.ykphn.yapgitsin.presentation.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.main.models.Food
import com.ykphn.yapgitsin.core.domain.repository.MealRepository
import com.ykphn.yapgitsin.presentation.main.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {
    private val _allCategoriesSelected = MutableStateFlow(value = true)
    val allCategoriesSelected: StateFlow<Boolean> = _allCategoriesSelected.asStateFlow()
    private val _category = MutableStateFlow<List<Category>>(emptyList())
    val category: StateFlow<List<Category>> = _category.asStateFlow()
    private val _food = MutableStateFlow<List<Food>>(emptyList())
    val food: StateFlow<List<Food>> = _food.asStateFlow()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val categoriesLoaded = loadCategories()
            if (categoriesLoaded) {
                _uiState.value = UiState.Success
                launch { loadFoods() }
            } else {
                _uiState.value = UiState.Error
            }
        }
    }


    private suspend fun loadCategories(): Boolean {
        val result = mealRepository.getCategories()
        return result.onSuccess {
            _category.value = it.categories.map { dto ->
                Category(dto.idCategory, dto.strCategory, true)
            }
        }.isSuccess
    }

    private suspend fun loadFoods(): Boolean {
        return try {
            val allFoods = mutableListOf<Food>()

            _category.value.forEach { category ->
                val result = mealRepository.getMealsByCategory(category.name)
                result.onSuccess { data ->
                    val mappedMeals = data.meals.map { dto ->
                        Food(
                            id = dto.idMeal,
                            categoryId = category.name,
                            name = dto.strMeal,
                            imageUrl = dto.strMealThumb ?: ""
                        )
                    }
                    allFoods.addAll(mappedMeals)
                    _food.value = allFoods.toList()
                }
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    fun selectAllCategories() {
        _allCategoriesSelected.value = true
        _category.value = _category.value.map { category ->
            category.copy(isSelected = true)
        }
    }

    fun toggleCategorySelection(index: Int) {
        if (_allCategoriesSelected.value) {
            _allCategoriesSelected.value = false
            _category.value = _category.value.mapIndexed { i, category ->
                if (i == index) category.copy(isSelected = true)
                else category.copy(isSelected = false)
            }
        } else {
            _category.value = _category.value.mapIndexed { i, category ->
                if (i == index) category.copy(isSelected = !category.isSelected)
                else category
            }
        }
        val isAllSelect = _category.value.all { it.isSelected }
        _allCategoriesSelected.value = isAllSelect
    }
}
