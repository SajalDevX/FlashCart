package me.mrsajal.flashcart.features.user_shop.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.user_shop.data.RemoteShopEntity

@Serializable
data class ShopApiResponseData(
    val success: Boolean,
    val message: String,
    val shop: RemoteShopEntity? = null
)
@Serializable
data class RemoteShopCategoryEntity(
    val shopCategoryId: String,
    val shopCategoryName: String
)
@Serializable
data class ShopCategoryApiResponseData(
    val success: Boolean,
    val message: String,
    val shopCategories: List<RemoteShopCategoryEntity> = emptyList()
)
data class ShopApiResponse(
    val data: ShopApiResponseData,
    val code:HttpStatusCode
)

data class ShopCategoryApiResponse(
    val data: ShopCategoryApiResponseData,
    val code:HttpStatusCode
)