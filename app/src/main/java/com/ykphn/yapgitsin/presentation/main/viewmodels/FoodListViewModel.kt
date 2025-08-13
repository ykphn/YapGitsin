package com.ykphn.yapgitsin.presentation.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.main.models.Food
import com.ykphn.yapgitsin.core.domain.repository.DatabaseRepository
import com.ykphn.yapgitsin.presentation.main.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val repository: DatabaseRepository
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
        val result = repository.getCategories()
        return result.onSuccess {
            _category.value = it.map { dto -> Category(dto.id, dto.name, true) }
        }.isSuccess
    }

    private suspend fun loadFoods(): Boolean {
        val result = repository.getFoods()
        return result.onSuccess {
            _food.value = it.map { dto ->
                Food(
                    dto.id, dto.categoryId, dto.name, dto.imageUrl, dto.time, dto.servings
                )
            }
        }.isSuccess
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
