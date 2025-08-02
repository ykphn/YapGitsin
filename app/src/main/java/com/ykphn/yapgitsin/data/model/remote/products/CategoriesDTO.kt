package com.ykphn.yapgitsin.data.model.remote.products

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDTO(
    val id: Int,
    val name: String
)
