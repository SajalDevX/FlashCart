package me.mrsajal.flashcart.features.product_review.data

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import me.mrsajal.flashcart.common.data.remote.KtorApi
import me.mrsajal.flashcart.features.product_review.domain.model.AddReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.model.EditReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.model.ReviewApiResponse

internal class ReviewApiService : KtorApi() {
    suspend fun addReview(
        userToken: String,
        addReview: AddReviewRequest,
    ): ReviewApiResponse {
        val httpResponse = client.post {
            endPoint(path = "review")
            setBody(addReview)
            setToken(userToken)
        }
        return ReviewApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
    suspend fun editReview(
        userToken: String,
        editReview: EditReviewRequest,
    ): ReviewApiResponse {
        val httpResponse = client.put {
            endPoint(path = "review")
            setBody(editReview)
            setToken(userToken)
        }
        return ReviewApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
    suspend fun getAllReviews(
        userToken: String,
        productId:String
    ): ReviewApiResponse {
        val httpResponse = client.get {
            endPoint(path = "review")
            parameter("productId",productId)
            setToken(userToken)
        }
        return ReviewApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }
    suspend fun deleteReview(
        userToken: String,
        productId:String
    ): ReviewApiResponse {
        val httpResponse = client.delete {
            endPoint(path = "review")
            parameter("productId",productId)
            setToken(userToken)
        }
        return ReviewApiResponse(
            code = httpResponse.status,
            data = httpResponse.body()
        )
    }

}