package me.mrsajal.flashcart.features.cart.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.cart.data.RemoteCartEntity
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity

@Serializable
data class CartApiResponseData(
    val success:Boolean,
    val message:String,
    val cart: RemoteCartEntity?=null
)

data class CartListData(
    val product:RemoteProductEntity,
    val qty:Int
)
data class CartApiResponse(
    val data:CartApiResponseData,
    val code:HttpStatusCode
)