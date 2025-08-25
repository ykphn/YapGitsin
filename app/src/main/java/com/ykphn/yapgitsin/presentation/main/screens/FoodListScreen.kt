package com.ykphn.yapgitsin.presentation.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.R
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.common.screens.ErrorScreen
import com.ykphn.yapgitsin.presentation.main.components.FoodCard
import com.ykphn.yapgitsin.presentation.main.viewmodels.FoodListViewModel
import com.ykphn.yapgitsin.presentation.main.models.Category
import com.ykphn.yapgitsin.presentation.main.models.Meal

@Composable
fun FoodListScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: FoodListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val foodList by viewModel.meal.collectAsState()
    val categories by viewModel.category.collectAsState()
    val allCategoriesSelected by viewModel.allCategoriesSelected.collectAsState()

    when (uiState) {

        UiState.Idle -> {
            EmptyScreen()
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
                mealList = foodList,
                selectAllCategories = { viewModel.selectAllCategories() },
                toggleCategorySelection = { viewModel.toggleCategorySelection(it) }
            )
        }

        UiState.Error -> {
            ErrorScreen(modifier = modifier)
        }
    }
}

@Composable
fun FoodListSuccessScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    allCategoriesSelected: Boolean,
    categories: List<Category>,
    mealList: List<Meal>,
    selectAllCategories: () -> Unit,
    toggleCategorySelection: (Int) -> Unit
) {
    Column {
        CategorySelector(
            allCategoriesSelected = allCategoriesSelected,
            categories = categories,
            allCategorySelected = { selectAllCategories() },
            onCategorySelected = { toggleCategorySelection(it) }
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) {
                val filteredFoodList = mealList.filter { food -> food.categoryId == it.name }
                if (it.isSelected && filteredFoodList.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(16.dp, 0.dp),
                        text = it.name,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    LazyRow(
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {} // Empty item to create left padding
                        items(filteredFoodList) { food ->
                            FoodCard(meal = food) {
                                navController.navigate("receipt/${food.id}")
                            }
                        }
                        item {} // Empty item to create left padding
                    }
                }
            }
            item {} // Empty item to create bottom padding
        }
    }
}

@Composable
fun CategorySelector(
    allCategoriesSelected: Boolean,
    categories: List<Category>,
    allCategorySelected: () -> Unit,
    onCategorySelected: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 0.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {} // Empty item to create left padding
        item {
            val backgroundColor =
                if (allCategoriesSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            val contentColor =
                if (allCategoriesSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(backgroundColor)
                    .clickable { allCategorySelected() }
                    .padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = stringResource(R.string.all),
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        itemsIndexed(categories) { index, it ->
            val backgroundColor =
                if (it.isSelected && !allCategoriesSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
            val contentColor =
                if (it.isSelected && !allCategoriesSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(backgroundColor)
                    .clickable { onCategorySelected(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = it.name,
                    color = contentColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        item {} // Empty item to create right padding
    }
}
