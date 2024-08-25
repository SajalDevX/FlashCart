package me.mrsajal.flashcart.features.order.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.order.data.OrderItems
import me.mrsajal.flashcart.features.order.data.RemoteOrderEntity

@Serializable
data class AddOrderRequest(
    val quantity: Int,
    val subTotal: Float,
    val total: Float,
    val shippingCharge: Float,
    val orderStatus: String?="pending",
    val orderItems: MutableList<OrderItems>
)

@Serializable
data class OrderData(
    val order: RemoteOrderEntity? = null,
    val orders: List<RemoteOrderEntity> = emptyList()
)

@Serializable
data class OrderResponseData(
    val success: Boolean,
    val message: String,
    val data: OrderData? = null
)
data class OrderApiResponse(
    val code: HttpStatusCode,
    val data: OrderResponseData
)