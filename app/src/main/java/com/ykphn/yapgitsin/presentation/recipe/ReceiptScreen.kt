package com.ykphn.yapgitsin.presentation.recipe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykphn.yapgitsin.core.common.state.UiState
import com.ykphn.yapgitsin.presentation.layouts.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.layouts.screens.ErrorScreen
import com.ykphn.yapgitsin.presentation.recipe.screens.ReceiptSuccessScreen

@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier, receiptId: Int
) {
    val viewModel: ReceiptViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val receipt by viewModel.receipt.collectAsState()

    LaunchedEffect(receiptId) {
        viewModel.loadInitialData(receiptId)
    }

    when (uiState) {
        UiState.Error -> {
            ErrorScreen(modifier = modifier)
        }

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
