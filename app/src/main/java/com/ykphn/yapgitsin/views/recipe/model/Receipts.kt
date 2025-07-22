package com.ykphn.yapgitsin.views.recipe.model

data class Receipts (
    val id: Int,
    val name: String,
    val description: String,
    val recipe: String,
    val ingredients: List<String>,
    val imageUrl: String,
    val time: String,
    val servings: String
)