package me.mrsajal.flashcart.features.cart.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.cart.domain.model.CartApiResponse

internal class CartApiService:KtorApi(){
    suspend fun addItemToCart(
        userToken:String,
        productId:String,
        qty:Int
    ): CartApiResponse{
        val httpResponse = client.post {
            endPoint("cart/$productId")
            parameter("qty",qty)
            setToken(userToken)
        }
        return CartApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
    suspend fun updateCartItems(
        userToken:String,
        productId:String,
        qty:Int
    ): CartApiResponse{
        val httpResponse = client.put {
            endPoint("cart/$productId")
            parameter("qty",qty)
            setToken(userToken)
        }
        return CartApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
    suspend fun removeFromCart(
        userToken:String,
        productId:String,
        qty:Int
    ): CartApiResponse{
        val httpResponse = client.delete {
            endPoint("cart/$productId")
            parameter("qty",qty)
            setToken(userToken)
        }
        return CartApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
    suspend fun getCartItems(
        userToken:String,
        offset:Int,
        limit:Int
    ): CartApiResponse{
        val httpResponse = client.get {
            endPoint("cart")
            parameter("offset", offset)
            parameter("limit", limit)
            setToken(userToken)
        }

        return CartApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
    suspend fun deleteAllCartItems(
        userToken:String
    ): CartApiResponse{
        val httpResponse = client.delete {
            endPoint("cart/all")
            setToken(userToken)
        }
        return CartApiResponse(
            data = httpResponse.body(),
            code = httpResponse.status
        )
    }
}