package com.ykphn.yapgitsin.presentation.main.models

data class Recipe(
    val imageUrl: String?,
    val name: String,
    val instructions: String?,
    val ingredients: List<Ingredients>
) {
    data class Ingredients(
        val name: String,
        val measure: String
    )
}