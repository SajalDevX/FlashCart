package me.mrsajal.flashcart.features.shipping.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteShippingEntity(
    val shippingId: String,
    var userId: String,
    var orderId: String,
    var shipAddress: String,
    var shipCity: String,
    var shipPhone: Int,
    var shipName: String?,
    var shipEmail: String?,
    var shipCountry: String?
)