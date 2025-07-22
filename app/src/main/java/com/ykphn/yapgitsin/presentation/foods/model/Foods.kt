package com.ykphn.yapgitsin.presentation.foods.model

data class Foods(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val imageUrl: String,
    val time: String,
    val servings: String,
)