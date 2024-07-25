package me.mrsajal.flashcart.features.product_review.data

import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.data.local.UserPreferences
import me.mrsajal.flashcart.common.utils.DispatcherProvider
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_review.domain.model.AddReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.model.EditReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.repository.ReviewRepository
import okio.IOException

internal class ReviewRepositoryImpl(
    private val reviewApiService: ReviewApiService,
    private val userPreferences: UserPreferences,
    private val dispatcher: DispatcherProvider
) : ReviewRepository {
    override suspend fun addReview(addReview: AddReviewRequest): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()

                val apiResponse = reviewApiService.addReview(userData.token, addReview)
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                            data = apiResponse.data.success,
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

    override suspend fun editReview(editReview: EditReviewRequest): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = reviewApiService.editReview(userData.token, editReview)
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


    override suspend fun getAllReviews(productId: String): Result<List<RemoteReviewEntity>> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = reviewApiService.getAllReviews(productId, userData.token)
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                           data = apiResponse.data.reviews
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

    override suspend fun deleteReview(productId: String): Result<Boolean> {
        return withContext(dispatcher.io) {
            try {
                val userData = userPreferences.getUserData()
                val apiResponse = reviewApiService.deleteReview(productId,userData.token)
                when (apiResponse.code) {

                    HttpStatusCode.OK -> {
                        Result.Success(
                                data = apiResponse.data.success,

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