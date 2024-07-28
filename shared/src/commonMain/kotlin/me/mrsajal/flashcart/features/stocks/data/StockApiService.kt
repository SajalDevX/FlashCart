package me.mrsajal.flashcart.features.stocks.data

import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.stocks.domain.model.StockApiResponse

internal class StockApiService : KtorApi() {
    suspend fun addStock(
        userToken: String,
        productId: String,
        shopId: String,
        qty: Int
    ): StockApiResponse {
        val httpResponse = client.post {
            endPoint("stock")
            setToken(userToken)
            parameter("productId", productId)
            parameter("shopId", shopId)
            parameter("qty", qty)
        }
        return StockApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getStocks(
        userToken: String,
        productId: String,
        shopId: String
    ): StockApiResponse {
        val httpResponse = client.post {
            endPoint("stock")
            setToken(userToken)
            parameter("productId", productId)
            parameter("shopId", shopId)
        }
        return StockApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun reduceStocks(
        userToken: String,
        productId: String,
        shopId: String,
        qty: Int
    ): StockApiResponse {
        val httpResponse = client.post {
            endPoint("stock")
            setToken(userToken)
            parameter("productId", productId)
            parameter("shopId", shopId)
            parameter("qty", qty)
        }
        return StockApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}