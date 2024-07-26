package me.mrsajal.flashcart.features.product_category.domain.repository

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.features.brands.data.BrandApiService
import me.mrsajal.flashcart.features.product_category.data.RemoteProductCategoryEntity
import me.mrsajal.flashcart.features.product_category.domain.model.CategoryTextParams
import okio.IOException
import  me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_category.data.CategoryApiService

internal class CategoryRepositoryImpl(
    private val categoryApiService: CategoryApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : CategoryRepository {
    override suspend fun createCategory(
        categoryData: CategoryTextParams,
        fileData: ByteArray,
        fileName: String
    ):Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = categoryApiService.addCategory(
                    userToken = userData.token,
                    categoryData = categoryData,
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

    override suspend fun deleteCategory(categoryId: String,userToken:String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = categoryApiService.deleteCategory(
                    userToken = userData.token,
                    categoryId = categoryId
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

    override suspend fun getCategory(
        userToken:String,
        limit: Int,
        offset: Int
    ): Result<List<RemoteProductCategoryEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = categoryApiService.getCategories(
                    userToken = userData.token,
                    limit = limit,
                    offset=offset
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.categories
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
        }    }
}