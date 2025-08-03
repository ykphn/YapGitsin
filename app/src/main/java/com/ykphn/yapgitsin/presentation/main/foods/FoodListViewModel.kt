package com.ykphn.yapgitsin.presentation.main.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.main.foods.model.Foods
import com.ykphn.yapgitsin.core.domain.repository.FoodRepository
import com.ykphn.yapgitsin.presentation.main.foods.model.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {
    private val _allCategoriesSelected = MutableStateFlow(value = true)
    val allCategoriesSelected: StateFlow<Boolean> = _allCategoriesSelected.asStateFlow()
    private val _categories = MutableStateFlow<List<Categories>>(emptyList())
    val categories: StateFlow<List<Categories>> = _categories.asStateFlow()
    private val _foods = MutableStateFlow<List<Foods>>(emptyList())
    val foods: StateFlow<List<Foods>> = _foods.asStateFlow()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val categoriesResult = loadCategories()
            val foodsResult = loadFoods()

            if (categoriesResult && foodsResult) {
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    private suspend fun loadCategories(): Boolean {
        val result = repository.getAllCategories()
        return result.onSuccess {
            _categories.value = it.map { dto -> Categories(dto.id, dto.name, true) }
        }.isSuccess
    }

    private suspend fun loadFoods(): Boolean {
        val result = repository.getAllFoods()
        return result.onSuccess {
            _foods.value = it.map { dto ->
                Foods(
                    dto.id, dto.categoryId, dto.name, dto.imageUrl, dto.time, dto.servings
                )
            }
        }.isSuccess
    }

    fun selectAllCategories() {
        _allCategoriesSelected.value = true
        _categories.value = _categories.value.map { category ->
            category.copy(isSelected = true)
        }
    }

    fun toggleCategorySelection(index: Int) {
        if (_allCategoriesSelected.value) {
            _allCategoriesSelected.value = false
            _categories.value = _categories.value.mapIndexed { i, category ->
                if (i == index) category.copy(isSelected = true)
                else category.copy(isSelected = false)
            }
        } else {
            _categories.value = _categories.value.mapIndexed { i, category ->
                if (i == index) category.copy(isSelected = !category.isSelected)
                else category
            }
        }
        val isAllSelect = _categories.value.all { it.isSelected }
        _allCategoriesSelected.value = isAllSelect
    }
}
