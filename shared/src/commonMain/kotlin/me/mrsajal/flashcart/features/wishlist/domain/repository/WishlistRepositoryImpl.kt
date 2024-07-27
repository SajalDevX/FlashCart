package me.mrsajal.flashcart.features.wishlist.domain.repository

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.wishlist.data.WishlistApiService
import okio.IOException

internal class WishlistRepositoryImpl(
    private val wishlistApiService: WishlistApiService,
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences
) : WishlistRepository {
    override suspend fun getWishlistItems(): Result<List<RemoteProductEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = wishlistApiService.getWishList(
                    userToken = userData.token
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.products
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

    override suspend fun deleteProductFromWishlist(productId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = wishlistApiService.removeItems(
                    userToken = userData.token,
                    productId = productId
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

    override suspend fun addToWishlist(productId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = wishlistApiService.createWishList(
                    userToken = userData.token,
                    productId = productId
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