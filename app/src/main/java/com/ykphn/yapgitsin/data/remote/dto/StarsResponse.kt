package com.ykphn.yapgitsin.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StarsResponse(
    val stars: List<Int>
)
