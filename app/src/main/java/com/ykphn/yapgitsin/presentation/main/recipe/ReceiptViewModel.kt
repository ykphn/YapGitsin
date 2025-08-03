package com.ykphn.yapgitsin.presentation.main.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.data.repository.FoodRepositoryImp
import com.ykphn.yapgitsin.presentation.main.recipe.model.Receipts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repository: FoodRepositoryImp
) : ViewModel() {
    private val _receiptList = MutableStateFlow<List<Receipts>>(emptyList())
    private val _receipt = MutableStateFlow<Receipts?>(null)
    val receipt: StateFlow<Receipts?> = _receipt.asStateFlow()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadInitialData(receiptId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val foodsResult = loadFoods()
            if (foodsResult) {
                _uiState.value = UiState.Success
                getReceipt(receiptId)
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    private suspend fun loadFoods(): Boolean {
        val result = repository.getAllFoods()
        return result.onSuccess {
            _receiptList.value = it.map { food ->
                Receipts(
                    id = food.id,
                    name = food.name,
                    description = food.description,
                    recipe = food.recipe,
                    ingredients = food.ingredients,
                    imageUrl = food.imageUrl,
                    time = food.time,
                    servings = food.servings
                )
            }
            _uiState.value = UiState.Success
        }.isSuccess
    }

    private fun getReceipt(index: Int) {
        _receipt.value = _receiptList.value.find { food -> food.id == index }
    }
}