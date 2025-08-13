package com.ykphn.yapgitsin.presentation.main.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screens.ErrorScreen
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.viewmodels.ReceiptViewModel
import com.ykphn.yapgitsin.presentation.main.models.Receipt

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
        UiState.Idle -> {EmptyScreen()}

        UiState.Error -> {
            ErrorScreen(modifier = modifier)
        }

        UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        UiState.Success -> {
            ReceiptSuccessScreen(
                modifier = modifier, receipt = receipt!!
            )
        }
    }
}

@Composable
fun ReceiptSuccessScreen(
    modifier: Modifier = Modifier,
    receipt: Receipt
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = receipt.imageUrl,
            contentDescription = receipt.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = receipt.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = receipt.description,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Süre: ${receipt.time}", fontWeight = FontWeight.Medium)
            Text(text = "Porsiyon: ${receipt.servings}", fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hazırlanış",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = receipt.instructions, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Malzemeler",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(receipt.ingredients) { ingredient ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(
                        text = ingredient,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
