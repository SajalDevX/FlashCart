package me.mrsajal.flashcart.features.cart.data

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.model.CartListData
import me.mrsajal.flashcart.features.cart.domain.repository.CartRepository
import me.mrsajal.flashcart.features.products.data.ProductApiService
import okio.IOException

internal class CartRepositoryImpl(
    private val cartApiService: CartApiService,
    private val productApiService: ProductApiService,
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences,
) : CartRepository {

    override suspend fun getCartItems(
        offset: Int,
        limit: Int
    ): Result<List<CartListData>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = cartApiService.getCartItems(
                    userToken = userData.token,
                    offset, limit
                )
                println("GetCartItems API Response: ${apiResponse.data.cart}")

                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        val cartItems = apiResponse.data.cart?.products ?: emptyMap()
                        println("Cart Items Map: $cartItems")

                        val deferredProducts = cartItems.keys.map { productId ->
                            async {
                                try {
                                    val productApiResponse = productApiService.getProductDetail(userData.token, productId)
                                    println("Fetched Product: ${productApiResponse.code}")

                                    val remoteProductEntity = productApiResponse.data.product
                                    if (remoteProductEntity != null) {
                                        CartListData(remoteProductEntity, cartItems[productId]!!)
                                    } else {
                                        println("Product is null for ID: $productId")
                                        null
                                    }
                                } catch (e: Exception) {
                                    println("Error fetching product with ID $productId: ${e.message}")
                                    null
                                }
                            }
                        }

                        val products = deferredProducts.awaitAll()
                            .filterNotNull()
                        println("Processed Products: $products")

                        Result.Success(products)
                    }

                    HttpStatusCode.BadRequest -> {
                        println("Error: ${apiResponse.data.message}")
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        println("Error: ${apiResponse.data.message}")
                        Result.Error(message = apiResponse.data.message)
                    }
                }
            } catch (ioException: IOException) {
                println("IOException: ${ioException.message}")
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                println("Exception: ${exception.message}")
                Result.Error(message = "${exception.message}")
            }
        }
    }



    override suspend fun addToCart(productId: String, qty: Int): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = cartApiService.addItemToCart(
                    userToken = userData.token,
                    productId, qty
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

    override suspend fun updateCartItems(productId: String, qty: Int): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = cartApiService.updateCartItems(
                    userToken = userData.token,
                    productId, qty
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

    override suspend fun removeFromCart(productId: String, qty: Int): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = cartApiService.removeFromCart(
                    userToken = userData.token,
                    productId, qty
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

    override suspend fun removeAllFromCart(): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = cartApiService.deleteAllCartItems(
                    userToken = userData.token,
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