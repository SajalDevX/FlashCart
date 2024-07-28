package me.mrsajal.flashcart.features.shipping.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.shipping.data.RemoteShippingEntity

@Serializable
data class AddShippingRequest(
    val orderId: String,
    val shippingAddress: String,
    val shipCity: String,
    val shipPhone: Int,
    val shipName: String,
    val shipEmail: String,
    val shipCountry: String
)

@Serializable
data class UpdateShippingRequest(
    val orderId: String,
    val shipAddress: String?,
    val shipCity: String?,
    val shipPhone: Int?,
    val shipName: String?,
    val shipEmail: String?,
    val shipCountry: String?
)
@Serializable
data class ShippingApiResponseData(
    val success:Boolean,
    val message:String,
    val data:RemoteShippingEntity?=null
)
data class ShippingApiResponse(
    val data:ShippingApiResponseData,
    val code:HttpStatusCode
)