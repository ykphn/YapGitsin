package com.ykphn.yapgitsin.presentation.main.models

data class Food(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val imageUrl: String,
    val preparationTime: String,
    val servings: String,
)