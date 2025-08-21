package com.ykphn.yapgitsin.presentation.main.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ykphn.yapgitsin.core.model.UiState
import com.ykphn.yapgitsin.presentation.common.screens.EmptyScreen
import com.ykphn.yapgitsin.presentation.common.screens.LoadingScreen
import com.ykphn.yapgitsin.presentation.common.screens.ErrorScreen
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
                    text = "Hepsi",
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

@Composable
fun FoodCard(meal: Meal, onClick: (String) -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .width(144.dp)
            .height(180.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(meal.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            val request = ImageRequest.Builder(LocalContext.current)
                .data(meal.imageUrl)
                .crossfade(true)
                .build()

            AsyncImage(
                model = request,
                contentDescription = meal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = meal.name,
                    maxLines = 2,
                    letterSpacing = 1.sp,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
