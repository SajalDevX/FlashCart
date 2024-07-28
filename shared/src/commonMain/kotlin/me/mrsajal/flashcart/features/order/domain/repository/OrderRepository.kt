package me.mrsajal.flashcart.features.order.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.order.data.OrderItems
import me.mrsajal.flashcart.features.order.data.RemoteOrderEntity

interface OrderRepository {
    suspend fun orderItems(
        quantity: Int,
        subTotal: Float,
        total: Float,
        shippingCharge: Float,
        orderItems: MutableList<OrderItems>
    ): Result<Boolean>

    suspend fun getOrders(
        offset: Int, limit: Int
    ): Result<List<RemoteOrderEntity>>

    suspend fun receivedOrder(
        orderId: String
    ): Result<Boolean>

    suspend fun cancelOrder(
        orderId: String
    ): Result<Boolean>

    suspend fun confirmOrder(
        orderId: String
    ): Result<Boolean>

    suspend fun deliverOrder(
        orderId: String
    ): Result<Boolean>

    suspend fun payOrder(
        orderId: String
    ): Result<Boolean>
}