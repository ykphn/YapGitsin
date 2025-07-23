package com.ykphn.yapgitsin.presentation.foods.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ykphn.yapgitsin.presentation.foods.model.Foods

@Composable
fun FoodCard(foods: Foods, onClick: (Int) -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(foods.id) }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {

            val request = ImageRequest.Builder(LocalContext.current)
                .data(foods.imageUrl)
                .crossfade(true)
                .build()

            AsyncImage(
                model = request,
                contentDescription = foods.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = foods.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Hazırlık: ${foods.time}", style = MaterialTheme.typography.bodySmall)
                Text(text = foods.servings, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

