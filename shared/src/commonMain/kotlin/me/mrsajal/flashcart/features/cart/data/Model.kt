package me.mrsajal.flashcart.features.cart.data

import kotlinx.serialization.Serializable


@Serializable
data class RemoteCartEntity(
    val cartId: String ,
    val userId: String,
    val products: Map<String, Int> = emptyMap()
)