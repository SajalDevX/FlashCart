package me.mrsajal.flashcart.features.product_review.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.product_review.data.RemoteReviewEntity


@Serializable
data class AddReviewRequest(
    val productId: String,
    val caption: String,
    val rating: Int,
)

@Serializable
data class EditReviewRequest(
    val productId: String,
    val caption: String,
    val rating: Int
)

@Serializable
data class ReviewApiResponseData(
    val success: Boolean,
    val message: String,
    val review: RemoteReviewEntity? = null,
    val reviews: List<RemoteReviewEntity> = emptyList()
)

data class ReviewApiResponse(
    val code: HttpStatusCode,
    val data: ReviewApiResponseData
)