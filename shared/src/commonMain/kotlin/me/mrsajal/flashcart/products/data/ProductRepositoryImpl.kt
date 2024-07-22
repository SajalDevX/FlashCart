package me.mrsajal.flashcart.products.data

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity
import okio.IOException
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Logger.Companion.setMinSeverity
import co.touchlab.kermit.Severity

internal class ProductRepositoryImpl(
    private val productApiService: ProductApiService,
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences
) : ProductRepository {

    private val logger = Logger.withTag("ProductRepository").apply {
        setMinSeverity(Severity.Debug)
    }

    override suspend fun addProduct(
        addProduct: AddProductRequest
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = productApiService.addProduct(userData.token, addProduct)
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success,
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }


    override suspend fun updateProduct(
        productId: String,
        updateProduct: UpdateProductRequest
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = productApiService.updateProduct(
                    userData.token,
                    productId = productId,
                    updateProduct
                )
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success,
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getProductById(
        productId: String
    ): Result<RemoteProductEntity> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = productApiService.getProductById(userData.token, productId)
                if (apiResponse.data.product == null) {
                    return@withContext Result.Error(message = "Product not found")
                }
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.product,
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun uploadImage(
        productId: String,
        imageFileName: String,
        imageBytes: ByteArray
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = productApiService.uploadImage(
                    userToken = userData.token,
                    productId = productId,
                    imageFileName = imageFileName,
                    imageBytes = imageBytes
                )
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success,
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }


    override suspend fun deleteProduct(
        productId: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = productApiService.deleteProduct(userData.token, productId)
                if (apiResponse.data.product == null) {
                    return@withContext Result.Error(message = "Product not found")
                }
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success,
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getProductDetail(
        productId: String
    ): Result<RemoteProductEntity> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = productApiService.getProductDetail(userData.token, productId)
                if (apiResponse.data.product == null) {
                    return@withContext Result.Error(message = "Product not found")
                }
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.product,
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getProductsForAll(
        limit: Int,
        offset: Int,
        maxPrice: Double?,
        minPrice: Double?
    ): Result<List<RemoteProductEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val userToken = userData.token
                logger.i { "User Token: $userToken $userData" }

                val apiResponse = productApiService.getProductsForAll(
                    userToken,
                    limit,
                    offset,
                    maxPrice,
                    minPrice,
                    categoryId = null,
                    subCategoryId = null,
                    brandId = null
                )


                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.allProducts)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        logger.e { "Error: ${apiResponse.data.message}" }
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getProductByCategory(categoryId: String): Result<List<RemoteProductEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val userToken = userData.token
                logger.i { "User Token: $userToken $userData" }

                val apiResponse = productApiService.getProductsForAll(
                    userToken = userToken,
                    categoryId = categoryId,
                    subCategoryId = null,
                    brandId = null,
                    limit = 10,
                    offset = 0,
                    maxPrice = null,
                    minPrice = null
                )


                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.allProducts)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        logger.e { "Error: ${apiResponse.data.message}" }
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getProductBySubCategory(subCategoryId: String): Result<List<RemoteProductEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val userToken = userData.token
                logger.i { "User Token: $userToken $userData" }

                val apiResponse = productApiService.getProductsForAll(
                    userToken = userToken,
                    categoryId = null,
                    subCategoryId = subCategoryId,
                    brandId = null,
                    limit = 10,
                    offset = 0,
                    maxPrice = null,
                    minPrice = null
                )


                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.allProducts)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        logger.e { "Error: ${apiResponse.data.message}" }
                        Result.Error(message = "${apiResponse.data.message}")
                    }
                }
            } catch (ioException: IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun getProductByBrand(brandId: String): Result<List<RemoteProductEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val userToken = userData.token
                logger.i { "User Token: $userToken $userData" }

                val apiResponse = productApiService.getProductsForAll(
                    userToken = userToken,
                    categoryId = null,
                    subCategoryId = null,
                    brandId = brandId,
                    limit = 10,
                    offset = 0,
                    maxPrice = null,
                    minPrice = null
                )


                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(data = apiResponse.data.allProducts)
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = "${apiResponse.data.message}")
                    }

                    else -> {
                        logger.e { "Error: ${apiResponse.data.message}" }
                        Result.Error(message = "${apiResponse.data.message}")
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