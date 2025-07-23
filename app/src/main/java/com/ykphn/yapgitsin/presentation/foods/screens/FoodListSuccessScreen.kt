package com.ykphn.yapgitsin.presentation.foods.screens

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ykphn.yapgitsin.presentation.foods.components.CategorySelector
import com.ykphn.yapgitsin.presentation.foods.components.FoodCard
import com.ykphn.yapgitsin.presentation.foods.model.Categories
import com.ykphn.yapgitsin.presentation.foods.model.Foods

@Composable
fun FoodListSuccessScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    allCategoriesSelected: Boolean,
    categories: List<Categories>,
    foodList: List<Foods>,
    selectAllCategories: () -> Unit,
    toggleCategorySelection: (Int) -> Unit
) {
    Column  {
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
