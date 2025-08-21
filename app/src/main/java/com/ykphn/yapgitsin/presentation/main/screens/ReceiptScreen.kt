package com.ykphn.yapgitsin.presentation.main.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import com.ykphn.yapgitsin.presentation.main.models.Recipe

@Composable
fun ReceiptScreen(
    modifier: Modifier = Modifier, recipeId: String
) {
    val viewModel: ReceiptViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val recipe by viewModel.recipe.collectAsState()

    LaunchedEffect(recipeId) {
        viewModel.loadInitialData(recipeId)
    }

    when (uiState) {
        UiState.Idle -> {
            EmptyScreen()
        }

        UiState.Error -> {
            ErrorScreen(modifier = modifier)
        }

        UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }

        UiState.Success -> {
            ReceiptSuccessScreen(
                modifier = modifier, recipe = recipe!!
            )
        }
    }
}

@Composable
fun ReceiptSuccessScreen(
    modifier: Modifier = Modifier,
    recipe: Recipe
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = "Food image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Hazırlanış",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = recipe.instructions ?: "",
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Malzemeler",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow (
                maxItemsInEachRow = 2, // aynı satırda 2 kart
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                recipe.ingredients.forEach { ingredient ->
                    OutlinedCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp)
                            .padding(0.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = ingredient.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = ingredient.measure,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

    }
}
