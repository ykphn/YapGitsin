package com.ykphn.yapgitsin.presentation.recipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykphn.yapgitsin.core.common.state.UiState
import com.ykphn.yapgitsin.presentation.foods.components.EmptyScreen
import com.ykphn.yapgitsin.presentation.recipe.screens.ReceiptSuccessScreen

@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier, receiptId: Int
) {
    val viewModel: ReceiptViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val receipt by viewModel.receipt.collectAsState()

    viewModel.loadInitialData(receiptId)

    when (uiState) {
        UiState.Error -> {}
        UiState.Loading -> {
            EmptyScreen(modifier = modifier)
        }

        UiState.Success -> {
            ReceiptSuccessScreen(
                modifier = modifier, receipt = receipt!!
            )
        }
    }
}
