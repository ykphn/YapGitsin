package com.ykphn.yapgitsin.views.foods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.views.foods.model.Foods
import com.ykphn.yapgitsin.data.repository.FoodRepository
import com.ykphn.yapgitsin.views.foods.model.Categories
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

    init {
        fetchCategories()
        fetchFoods()
    }

    private val _allCategoriesSelected = MutableStateFlow(value = true)
    val allCategoriesSelected: StateFlow<Boolean> = _allCategoriesSelected.asStateFlow()
    private val _categories = MutableStateFlow<List<Categories>>(emptyList())
    val categories: StateFlow<List<Categories>> = _categories.asStateFlow()
    private val _foods = MutableStateFlow<List<Foods>>(emptyList())
    val foods: StateFlow<List<Foods>> = _foods.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchCategories() {
        viewModelScope.launch {
            val result = repository.getAllCategories()
            result.onSuccess {
                _categories.value = it.map { category ->
                    Categories(
                        id = category.id, name = category.name, isSelected = true
                    )
                }
            }.onFailure { _error.value = it.message }
        }
    }

    fun fetchFoods() {
        viewModelScope.launch {
            val result = repository.getAllFoods()
            result.onSuccess {
                _foods.value = it.map { food ->
                    Foods(
                        id = food.id,
                        categoryId = food.categoryId,
                        name = food.name,
                        imageUrl = food.imageUrl,
                        time = food.time,
                        servings = food.servings
                    )
                }
            }.onFailure { _error.value = it.message }
        }
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
    }
}
