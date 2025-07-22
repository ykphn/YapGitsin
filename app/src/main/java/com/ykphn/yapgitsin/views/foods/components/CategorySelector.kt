package com.ykphn.yapgitsin.views.foods.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ykphn.yapgitsin.views.foods.model.Categories


@Composable
fun CategorySelector(
    allCategoriesSelected: Boolean,
    categories: List<Categories>,
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
