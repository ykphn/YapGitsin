package com.ykphn.yapgitsin.data.model

import com.google.gson.annotations.SerializedName


data class FoodDTO(
    val id: Int,
    @SerializedName("category_id") val categoryId: Int,
    val name: String,
    val description: String,
    val recipe: String,
    val ingredients: List<String>,
    @SerializedName("image_url") val imageUrl: String,
    val time: String,
    val servings: String
)



