package me.mrsajal.flashcart.features.stocks.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteStockEntity(
    val stockId:String,
    val productId: String,
    val shopId:String,
    val quantity:Int
)