package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FoodDTO(
    val id: Int,
    @SerialName("category_id")
    val categoryId: Int,
    val name: String,
    val description: String,
    val recipe: String,
    val ingredients: List<String>,
    @SerialName("image_url")
    val imageUrl: String,
    val time: String,
    val servings: String
)



