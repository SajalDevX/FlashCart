package me.mrsajal.flashcart.features.shipping.domain.repository

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.features.shipping.data.RemoteShippingEntity
import me.mrsajal.flashcart.features.shipping.data.ShippingApiService
import me.mrsajal.flashcart.features.shipping.domain.model.AddShippingRequest
import okio.IOException
import me.mrsajal.flashcart.common.utils.Result


internal class ShippingRepositoryImpl(
    private val shippingApiService: ShippingApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : ShippingRepository {
    override suspend fun addShipping(addShippingRequest: AddShippingRequest): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shippingApiService.addShipping(
                    userToken = userData.token,
                    addShipping = addShippingRequest
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

    override suspend fun getShipping(orderId: String): Result<RemoteShippingEntity> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shippingApiService.getShipping(
                    userToken = userData.token,
                    orderId = orderId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.data!!
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

    override suspend fun updateShipping(
        orderId: String,
        shippingAddress: String,
        shipCity: String,
        shipPhone: Int,
        shipName: String,
        shipEmail: String,
        shipCountry: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shippingApiService.updateShipping(
                    userToken = userData.token,
                    orderId = orderId,
                    shippingAddress = shippingAddress,
                    shipCity = shipCity,
                    shipPhone = shipPhone,
                    shipName = shipName,
                    shipEmail = shipEmail,
                    shipCountry = shipCountry
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

    override suspend fun deleteShipping(orderId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shippingApiService.deleteShipping(
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