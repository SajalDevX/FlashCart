package me.mrsajal.flashcart.features.user_shop.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.user_shop.domain.model.ShopApiResponse
import me.mrsajal.flashcart.features.user_shop.domain.model.ShopCategoryApiResponse

internal class ShopApiService : KtorApi() {

    suspend fun addShopCategory(
        shopCategoryName: String,
        userToken: String
    ): ShopCategoryApiResponse {
        val httpResponse = client.post {
            endPoint("shop/category")
            parameter("shopCategoryName", shopCategoryName)
            setToken(userToken)
        }
        return ShopCategoryApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getShopCategory(
        userToken: String,
        offset: Int,
        limit: Int
    ): ShopCategoryApiResponse {
        val httpResponse = client.get {
            endPoint("shop/category")
            parameter("limit", limit)
            parameter("offset", offset)
            setToken(userToken)
        }
        return ShopCategoryApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deleteShopCategory(
        shopCategoryId: String,
        userToken: String
    ): ShopCategoryApiResponse {
        val httpResponse = client.delete {
            endPoint("shop/category")
            parameter("shopCategoryId", shopCategoryId)
            setToken(userToken)
        }
        return ShopCategoryApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun updateShopCategory(
        shopCategoryId: String,
        shopCategoryName: String,
        userToken: String
    ): ShopCategoryApiResponse {
        val httpResponse = client.post {
            endPoint("shop/category")
            parameter("shopCategoryId", shopCategoryId)
            parameter("shopCategoryName", shopCategoryName)
            setToken(userToken)
        }
        return ShopCategoryApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun addShop(
        shopName: String,
        shopCategoryId: String,
        userToken: String
    ): ShopApiResponse {
        val httpResponse = client.post {
            endPoint("shop/add-shop")
            parameter("shopName", shopName)
            parameter("shopCategoryId", shopCategoryId)
            setToken(userToken)
        }
        return ShopApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

}