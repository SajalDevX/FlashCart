package me.mrsajal.flashcart.features.order.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteOrderEntity(
    val orderId: String,
    val userId: String,
    val paymentId: String? = null,
    val paymentType: String? = null,
    val quantity: Int,
    val subTotal: Float,
    val shippingCharge: Float = 0.0f,
    val vat: Float? = null,
    val cancelOrder: Boolean = false,
    val coupon: String? = null,
    val status: String = "pending",
    val statusCode: Int = 0,
    val orderItems: List<OrderItems> = emptyList()
)

@Serializable
data class OrderItems(
    val productId: String,
    val quantity: Int
)