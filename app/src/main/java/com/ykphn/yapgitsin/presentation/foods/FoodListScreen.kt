package com.ykphn.yapgitsin.presentation.foods

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.core.common.state.UiState
import com.ykphn.yapgitsin.presentation.foods.screens.FoodListLoadingScreen
import com.ykphn.yapgitsin.presentation.foods.screens.FoodListSuccessScreen

@Composable
fun FoodListScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: FoodListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val foodList by viewModel.foods.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val allCategoriesSelected by viewModel.allCategoriesSelected.collectAsState()

    when (uiState) {
        UiState.Error -> {
            Text(text = "Hata Oluştu")
        }

        UiState.Loading -> {
            FoodListLoadingScreen(
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

