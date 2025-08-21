package com.ykphn.yapgitsin.data.remote.dto

import com.ykphn.yapgitsin.presentation.main.models.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(
    val meals: List<MealRecipe>
) {
    @Serializable
    data class MealRecipe(
        val idMeal: String,
        val strMeal: String,
        val strMealAlternate: String? = null,
        val strCategory: String? = null,
        val strArea: String? = null,
        val strInstructions: String? = null,
        val strMealThumb: String? = null,
        val strTags: String? = null,
        val strYoutube: String? = null,
        val strIngredient1: String? = null,
        val strIngredient2: String? = null,
        val strIngredient3: String? = null,
        val strIngredient4: String? = null,
        val strIngredient5: String? = null,
        val strIngredient6: String? = null,
        val strIngredient7: String? = null,
        val strIngredient8: String? = null,
        val strIngredient9: String? = null,
        val strIngredient10: String? = null,
        val strIngredient11: String? = null,
        val strIngredient12: String? = null,
        val strIngredient13: String? = null,
        val strIngredient14: String? = null,
        val strIngredient15: String? = null,
        val strIngredient16: String? = null,
        val strIngredient17: String? = null,
        val strIngredient18: String? = null,
        val strIngredient19: String? = null,
        val strIngredient20: String? = null,
        val strMeasure1: String? = null,
        val strMeasure2: String? = null,
        val strMeasure3: String? = null,
        val strMeasure4: String? = null,
        val strMeasure5: String? = null,
        val strMeasure6: String? = null,
        val strMeasure7: String? = null,
        val strMeasure8: String? = null,
        val strMeasure9: String? = null,
        val strMeasure10: String? = null,
        val strMeasure11: String? = null,
        val strMeasure12: String? = null,
        val strMeasure13: String? = null,
        val strMeasure14: String? = null,
        val strMeasure15: String? = null,
        val strMeasure16: String? = null,
        val strMeasure17: String? = null,
        val strMeasure18: String? = null,
        val strMeasure19: String? = null,
        val strMeasure20: String? = null,
        val strSource: String? = null,
        val strImageSource: String? = null,
        val strCreativeCommonsConfirmed: String? = null,
        val dateModified: String? = null
    ) {
        fun toDomain(): Recipe {
            val ingredients = listOfNotNull(
                strIngredient1?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure1.orEmpty()) },
                strIngredient2?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure2.orEmpty()) },
                strIngredient3?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure3.orEmpty()) },
                strIngredient4?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure4.orEmpty()) },
                strIngredient5?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure5.orEmpty()) },
                strIngredient6?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure6.orEmpty()) },
                strIngredient7?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure7.orEmpty()) },
                strIngredient8?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure8.orEmpty()) },
                strIngredient9?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure9.orEmpty()) },
                strIngredient10?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure10.orEmpty()) },
                strIngredient11?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure11.orEmpty()) },
                strIngredient12?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure12.orEmpty()) },
                strIngredient13?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure13.orEmpty()) },
                strIngredient14?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure14.orEmpty()) },
                strIngredient15?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure15.orEmpty()) },
                strIngredient16?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure16.orEmpty()) },
                strIngredient17?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure17.orEmpty()) },
                strIngredient18?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure18.orEmpty()) },
                strIngredient19?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure19.orEmpty()) },
                strIngredient20?.takeIf { it.isNotBlank() }
                    ?.let { Recipe.Ingredients(it, strMeasure20.orEmpty()) }
            )
            return Recipe(
                name = strMeal,
                imageUrl = strMealThumb,
                instructions = strInstructions,
                ingredients = ingredients
            )
        }
    }
}