package me.mrsajal.flashcart.features.brands.domain.model

import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.brands.data.RemoteBrandEntity

@Serializable
data class BrandApiResponse(
    val success:Boolean,
    val message:String,
    val brands:List<RemoteBrandEntity?> = emptyList()
)