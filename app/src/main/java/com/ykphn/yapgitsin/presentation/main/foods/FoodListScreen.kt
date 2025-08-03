package com.ykphn.yapgitsin.presentation.main.foods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.main.foods.screens.FoodListSuccessScreen
import com.ykphn.yapgitsin.presentation.common.screens.ErrorScreen

@Composable
fun FoodListScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: FoodListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val foodList by viewModel.foods.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val allCategoriesSelected by viewModel.allCategoriesSelected.collectAsState()

    when (uiState) {
        UiState.Error -> {
            ErrorScreen(modifier = modifier)
        }

        UiState.Loading -> {
            LoadingScreen(
                modifier = modifier
            )
        }

        UiState.Success -> {
            FoodListSuccessScreen(
                modifier = modifier,
                navController = navController,
                allCategoriesSelected = allCategoriesSelected,
                categories = categories,
                foodList = foodList,
                selectAllCategories = { viewModel.selectAllCategories() },
                toggleCategorySelection = { viewModel.toggleCategorySelection(it) }
            )
        }
    }
}

