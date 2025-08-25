package com.ykphn.yapgitsin.presentation.main.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.ykphn.yapgitsin.R
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
                modifier = modifier,
                recipe = recipe!!,
                isStarred = viewModel.isStarred,
                addFavorite = { viewModel.addFavorite(recipeId.toInt()) }
            )
        }
    }
}

@Composable
fun ReceiptSuccessScreen(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    isStarred: Boolean,
    addFavorite: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Box {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = stringResource(R.string.food_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(bottom = 16.dp)
                )

                IconButton(
                    onClick = addFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Filled.Star,
                        contentDescription = stringResource(R.string.star_button),
                        tint =
                            if (isStarred) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                    )
                }
            }


            Text(
                text = stringResource(R.string.instructions),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = recipe.instructions ?: stringResource(R.string.empty_string),
                fontSize = 12.sp,
                letterSpacing = 1.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.ingredients),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            FlowRow(
                maxItemsInEachRow = 2,
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
                            .height(80.dp),
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
