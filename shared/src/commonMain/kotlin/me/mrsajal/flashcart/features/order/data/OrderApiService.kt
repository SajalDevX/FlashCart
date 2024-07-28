package me.mrsajal.flashcart.features.order.data

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.brands.domain.model.BrandApiResponse
import me.mrsajal.flashcart.features.order.domain.model.AddOrderRequest
import me.mrsajal.flashcart.features.order.domain.model.OrderApiResponse

internal class OrderApiService : KtorApi() {

    suspend fun addOrder(
        addOrder: AddOrderRequest,
        userToken: String
    ): OrderApiResponse {
        val httpResponse = client.post {
            endPoint("order")
            setBody(addOrder)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getOrders(
        userToken: String,
        offset:Int,
        limit:Int
    ): OrderApiResponse {
        val httpResponse = client.get {
            endPoint("order")
            parameter("offset",offset)
            parameter("limit",limit)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun orderPayment(
        orderId: String,
        userToken: String
    ): OrderApiResponse {
        val httpResponse = client.put {
            endPoint("order/payment")
            setBody(orderId)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun receivedOrders(
        orderId: String,
        userToken: String
    ): OrderApiResponse {
        val httpResponse = client.put {
            endPoint("order/receive")
            setBody(orderId)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun cancelOrder(
        userToken: String,
        orderId: String
    ): OrderApiResponse {
        val httpResponse = client.put {
            endPoint("order/cancel")
            parameter("orderId",orderId)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun confirmOrder(
        userToken: String,
        orderId: String
    ): OrderApiResponse {
        val httpResponse = client.put {
            endPoint("order/confirm")
            parameter("orderId", orderId)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deliverOrder(
        userToken: String,
        orderId: String
    ): OrderApiResponse {
        val httpResponse = client.put {
            endPoint("order/deliver")
            parameter("orderId", orderId)
            setToken(userToken)
        }
        return OrderApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}