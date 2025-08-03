package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoriesDTO(
    val id: Int,
    val name: String
)
