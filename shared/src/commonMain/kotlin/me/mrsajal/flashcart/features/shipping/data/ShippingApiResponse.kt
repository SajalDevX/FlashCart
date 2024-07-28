package me.mrsajal.flashcart.features.shipping.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.shipping.domain.model.AddShippingRequest
import me.mrsajal.flashcart.features.shipping.domain.model.ShippingApiResponse

internal class ShippingApiService : KtorApi() {
    suspend fun addShipping(
        addShipping: AddShippingRequest,
        userToken: String,
    ): ShippingApiResponse {
        val httpResponse = client.post {
            endPoint("shipping")
            setBody(addShipping)
            setToken(userToken)
        }
        return ShippingApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun getShipping(
        orderId: String,
        userToken: String,
    ): ShippingApiResponse {
        val httpResponse = client.get {
            endPoint("shipping")
            parameter("orderId", orderId)
            setToken(userToken)
        }
        return ShippingApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun updateShipping(
        userToken: String,
        orderId: String,
        shippingAddress: String,
        shipCity: String,
        shipPhone: Int,
        shipName: String,
        shipEmail: String,
        shipCountry: String,
    ): ShippingApiResponse {
        val httpResponse = client.put {
            endPoint("shipping/$orderId")
            parameter("shippingAddress", shippingAddress)
            parameter("shipCity", shipCity)
            parameter("shipPhone", shipPhone)
            parameter("shipName", shipName)
            parameter("shipEmail", shipEmail)
            parameter("shipCountry", shipCountry)
            setToken(userToken)
        }
        return ShippingApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

    suspend fun deleteShipping(
        userToken: String,
        orderId: String,
    ): ShippingApiResponse {
        val httpResponse = client.delete {
            endPoint("shipping/$orderId")
            setToken(userToken)
        }
        return ShippingApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
}