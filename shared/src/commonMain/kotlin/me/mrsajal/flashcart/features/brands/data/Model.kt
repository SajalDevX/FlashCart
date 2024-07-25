package me.mrsajal.flashcart.features.brands.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteBrandEntity(
    val brandId: String,
    val brandName: String,
    val brandLogo: String? = null
)