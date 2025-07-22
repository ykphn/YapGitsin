package com.ykphn.yapgitsin.views.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykphn.yapgitsin.data.repository.FoodRepository
import com.ykphn.yapgitsin.views.recipe.model.Receipts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {
    init {
        fetchFoods()
    }

    private val _receiptList = MutableStateFlow<List<Receipts>>(emptyList())
    val receiptList: StateFlow<List<Receipts>> = _receiptList.asStateFlow()
    private val _receipt = MutableStateFlow<Receipts?>(null)
    val receipt: StateFlow<Receipts?> = _receipt.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)

    private fun fetchFoods() {
        viewModelScope.launch {
            val result = repository.getAllFoods()
            result.onSuccess {
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
            }.onFailure { _error.value = it.message }
        }
    }

    fun getReceipt(index: Int) {
        _receipt.value = _receiptList.value.find { food -> food.id == index }
    }

}