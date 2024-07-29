package me.mrsajal.flashcart.features.user_shop.domain.repository

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.user_shop.data.RemoteShopEntity
import me.mrsajal.flashcart.features.user_shop.data.ShopApiService
import me.mrsajal.flashcart.features.user_shop.domain.model.RemoteShopCategoryEntity
import okio.IOException

internal class ShopRepositoryImpl(
    private val shopApiService: ShopApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : ShopRepository {
    override suspend fun addShopCategory(shopCategoryName: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shopApiService.addShopCategory(
                    userToken = userData.token,
                    shopCategoryName = shopCategoryName
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

    override suspend fun getShopCategory(
        offset: Int,
        limit: Int
    ): Result<List<RemoteShopCategoryEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shopApiService.getShopCategory(
                    userToken = userData.token,
                    limit = limit,
                    offset = offset
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.shopCategories
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

    override suspend fun deleteShopCategory(shopCategoryId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shopApiService.deleteShopCategory(
                    userToken = userData.token,
                    shopCategoryId = shopCategoryId
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

    override suspend fun updateShopCategory(
        shopCategoryId: String,
        shopCategoryName: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shopApiService.updateShopCategory(
                    userToken = userData.token,
                    shopCategoryId = shopCategoryId,
                    shopCategoryName = shopCategoryName
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

    override suspend fun addShop(shopName: String, shopCategoryId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shopApiService.addShop(
                    userToken = userData.token,
                    shopName = shopName,
                    shopCategoryId = shopCategoryId
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

    override suspend fun getShop(): Result<RemoteShopEntity> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = shopApiService.getShop(
                    userToken = userData.token
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.shop!!
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