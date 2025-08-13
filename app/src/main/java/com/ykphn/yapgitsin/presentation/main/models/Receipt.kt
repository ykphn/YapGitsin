package com.ykphn.yapgitsin.presentation.main.models

data class Receipt(
    val id: Int,
    val title: String,
    val description: String,
    val instructions: String,
    val ingredients: List<String>,
    val imageUrl: String,
    val time: String,
    val servings: String
)