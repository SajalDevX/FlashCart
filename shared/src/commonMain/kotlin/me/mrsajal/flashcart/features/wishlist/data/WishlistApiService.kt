package me.mrsajal.flashcart.features.wishlist.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.wishlist.domain.model.WishlistResponse

internal class WishlistApiService : KtorApi() {
    suspend fun getWishList(
        userToken: String,
    ): WishlistResponse {
        val httpsResponse = client.get {
            endPoint("wishlist")
            setToken(userToken)
        }
        return WishlistResponse(
            data = httpsResponse.body(),
            code = httpsResponse.status
        )
    }

    suspend fun createWishList(
        userToken: String,
        productId: String,
    ): WishlistResponse {
        val httpsResponse = client.post {
            endPoint("wishlist")
            parameter("productId", productId)
            setToken(userToken)
        }
        return WishlistResponse(
            data = httpsResponse.body(),
            code = httpsResponse.status
        )
    }

    suspend fun removeItems(
        userToken: String,
        productId: String,
    ): WishlistResponse {
        val httpsResponse = client.delete {
            endPoint("wishlist")
            parameter("productId", productId)
            setToken(userToken)
        }
        return WishlistResponse(
            data = httpsResponse.body(),
            code = httpsResponse.status
        )
    }
}