package me.mrsajal.flashcart.features.wishlist.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity

@Serializable
data class WishlistResponseData(
    val success: Boolean,
    val message: String,
    val product: RemoteProductEntity? = null,
    val products: List<RemoteProductEntity> = emptyList()
)

data class WishlistResponse(
    val data: WishlistResponseData,
    val code: HttpStatusCode
)