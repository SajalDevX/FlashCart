package me.mrsajal.flashcart.features.shipping.domain.repository

import me.mrsajal.flashcart.features.shipping.data.RemoteShippingEntity
import me.mrsajal.flashcart.features.shipping.domain.model.AddShippingRequest
import me.mrsajal.flashcart.common.utils.Result
interface ShippingRepository {
    suspend fun addShipping(addShippingRequest: AddShippingRequest): Result<Boolean>
    suspend fun getShipping(orderId: String): Result<RemoteShippingEntity>
    suspend fun updateShipping(
        orderId: String,
        shippingAddress: String,
        shipCity: String,
        shipPhone: Int,
        shipName: String,
        shipEmail: String,
        shipCountry: String,
    ): Result<Boolean>

    suspend fun deleteShipping(orderId: String): Result<Boolean>
}