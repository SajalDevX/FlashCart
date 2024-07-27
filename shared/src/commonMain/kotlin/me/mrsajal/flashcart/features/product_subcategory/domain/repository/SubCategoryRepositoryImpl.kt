package me.mrsajal.flashcart.features.product_subcategory.domain.repository

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.features.product_subcategory.data.RemoteProductSubCategoryEntity
import me.mrsajal.flashcart.features.product_subcategory.data.SubCategoryApiService
import me.mrsajal.flashcart.features.product_subcategory.domain.model.SubCategoryTextParams
import me.mrsajal.flashcart.common.utils.Result

internal class SubCategoryRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val subCategoryApiService: SubCategoryApiService,
    private val dispatcher: DispatcherProvider
) : SubCategoryRepository {

    override suspend fun createSubCategory(
        subCategoryData: SubCategoryTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        return withContext(dispatcher.io){
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = subCategoryApiService.createSubcategory(
                    userToken = userData.token,
                    subCategoryData = subCategoryData,
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
            }catch (ioException: okio.IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }
    }

    override suspend fun deleteSubCategory(subCategoryId: String): Result<Boolean> {
        return withContext(dispatcher.io){
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = subCategoryApiService.deleteSubCategory(
                    userToken = userData.token,
                    subCategoryId = subCategoryId
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
            }catch (ioException: okio.IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }    }

    override suspend fun getSubCategories(
        categoryId: String,
        limit: Int,
        offset: Int
    ): Result<List<RemoteProductSubCategoryEntity>> {
        return withContext(dispatcher.io){
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = subCategoryApiService.getSubCategories(
                    userToken = userData.token,
                    limit = limit,
                    offset = offset,
                    categoryId = categoryId
                )
                when (apiResponse.code) {
                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.subCategories
                        )
                    }

                    HttpStatusCode.BadRequest -> {
                        Result.Error(message = apiResponse.data.message)
                    }

                    else -> {
                        Result.Error(message = apiResponse.data.message)
                    }
                }
            }catch (ioException: okio.IOException) {
                Result.Error(message = "No Internet")
            } catch (exception: Throwable) {
                Result.Error(message = "${exception.message}")
            }
        }    }
}