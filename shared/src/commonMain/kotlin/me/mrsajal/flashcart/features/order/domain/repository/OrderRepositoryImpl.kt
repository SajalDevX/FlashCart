package me.mrsajal.flashcart.features.order.domain.repository

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.data.BrandApiService
import me.mrsajal.flashcart.features.order.data.OrderApiService
import me.mrsajal.flashcart.features.order.data.OrderItems
import me.mrsajal.flashcart.features.order.data.RemoteOrderEntity
import me.mrsajal.flashcart.features.order.domain.model.AddOrderRequest
import okio.IOException

internal class OrderRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences,
    private val orderApiService: OrderApiService
) : OrderRepository {
    override suspend fun orderItems(
        quantity: Int,
        subTotal: Float,
        total: Float,
        shippingCharge: Float,
        orderItems: MutableList<OrderItems>
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val orderDetails = AddOrderRequest(
                    quantity = quantity,
                    subTotal = subTotal,
                    total = total,
                    shippingCharge = shippingCharge,
                    orderItems = orderItems
                )
                val apiResponse = orderApiService.addOrder(
                    userToken = userData.token,
                    addOrder = orderDetails
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getOrders(offset: Int, limit: Int): Result<List<RemoteOrderEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = orderApiService.getOrders(
                    userToken = userData.token,
                    offset = offset,
                    limit = limit
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data?.orderData?.orders?: emptyList()
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun receivedOrder(orderId: String): Result<Boolean> {

        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = orderApiService.receivedOrders(
                    userToken = userData.token,
                    orderId = orderId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun cancelOrder(orderId: String): Result<Boolean> {
        return withContext(dispatcher.io){
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = orderApiService.cancelOrder(
                    userToken = userData.token,
                    orderId = orderId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun confirmOrder(orderId: String): Result<Boolean> {
                return withContext(dispatcher.io){
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = orderApiService.confirmOrder(
                    userToken = userData.token,
                    orderId = orderId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun deliverOrder(orderId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = orderApiService.deliverOrder(
                    userToken = userData.token,
                    orderId = orderId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun payOrder(orderId: String): Result<Boolean> {
        return withContext(dispatcher.io){
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = orderApiService.orderPayment(
                    userToken = userData.token,
                    orderId = orderId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }

            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }
}