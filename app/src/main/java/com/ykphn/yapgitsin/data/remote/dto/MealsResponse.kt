package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse(
    val meals: List<Meal>
) {
    @Serializable
    data class Meal (
        val idMeal: String,
        val strMeal: String,
        val strMealThumb: String?,
    )
}