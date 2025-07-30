package com.ykphn.yapgitsin.presentation.recipe.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ykphn.yapgitsin.presentation.recipe.model.Receipts

@Composable
fun ReceiptSuccessScreen(
    modifier: Modifier = Modifier,
    receipt: Receipts
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = receipt.imageUrl,
            contentDescription = receipt.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = receipt.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = receipt.description,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Süre: ${receipt.time}", fontWeight = FontWeight.Medium)
            Text(text = "Porsiyon: ${receipt.servings}", fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Hazırlanış",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = receipt.recipe, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Malzemeler",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(receipt.ingredients) { ingredient ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = "• $ingredient",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiptSuccessScreenPreview() {
    val dummy = Receipts(
        id = 1,
        name = "Lorem Ipsum Yemeği",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        recipe = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        ingredients = listOf(
            "Lorem ingredient 1",
            "Lorem ingredient 2",
            "Lorem ingredient 3",
            "Lorem ingredient 4"
        ),
        imageUrl = "",
        time = "30 dk",
        servings = "2 kişilik",
    )
    ReceiptSuccessScreen(receipt = dummy)
}

