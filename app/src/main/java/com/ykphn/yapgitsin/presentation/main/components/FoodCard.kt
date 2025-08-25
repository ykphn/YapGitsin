package com.ykphn.yapgitsin.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ykphn.yapgitsin.presentation.main.models.Meal

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