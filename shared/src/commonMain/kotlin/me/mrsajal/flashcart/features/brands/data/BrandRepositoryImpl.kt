package me.mrsajal.flashcart.features.brands.data

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.domain.model.BrandTextParams
import me.mrsajal.flashcart.features.brands.domain.repositpry.BrandRepository
import okio.IOException

internal class BrandRepositoryImpl(
    private val brandApiService: BrandApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : BrandRepository {

    override suspend fun createBrands(
        brandData: BrandTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = brandApiService.addBrand(
                    userToken = userData.token,
                    brandData = brandData,
                    fileData = fileData,
                    fileName = fileName
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


    override suspend fun getBrands(
        offset: Int,
        limit: Int
    ): Result<List<RemoteBrandEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = brandApiService.getBrands(
                    userToken = userData.token,
                    offset = offset,
                    limit = limit
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.brands
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

    override suspend fun deleteBrand(brandId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = brandApiService.deleteBrand(
                    userToken = userData.token, brandId = brandId
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