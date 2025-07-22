package com.ykphn.yapgitsin.presentation.foods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykphn.yapgitsin.presentation.foods.components.CategorySelector
import com.ykphn.yapgitsin.presentation.foods.components.FoodCard

@Composable
fun FoodListScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: FoodListViewModel = hiltViewModel()
    val foodList by viewModel.foods.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val allCategoriesSelected by viewModel.allCategoriesSelected.collectAsState()

    Column  {
        CategorySelector(
            allCategoriesSelected = allCategoriesSelected,
            categories = categories,
            allCategorySelected = {viewModel.selectAllCategories()},
            onCategorySelected = {viewModel.toggleCategorySelection(it)}
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) {
                if (it.isSelected) {
                    Text(modifier = Modifier.padding(16.dp, 0.dp), text = it.name, fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                    LazyRow (
                        modifier = modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {} // Empty item to create left padding
                        val filteredFoodList = foodList.filter { food -> food.categoryId == it.id }
                        items(filteredFoodList) { food ->
                            FoodCard(foods = food) {
                                navController.navigate("receipt/${food.id}")
                            }
                        }
                        item {} // Empty item to create left padding
                    }
                }
            }
            item{} // Empty item to create bottom padding
        }
    }
}

