package me.mrsajal.flashcart.features.product_review.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_review.data.RemoteReviewEntity
import me.mrsajal.flashcart.features.product_review.domain.model.AddReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.model.EditReviewRequest

interface ReviewRepository {
    suspend fun addReview(addReview:AddReviewRequest):Result<Boolean>
    suspend fun editReview(editReview: EditReviewRequest):Result<Boolean>
    suspend fun getAllReviews(productId:String):Result<List<RemoteReviewEntity>>
    suspend fun deleteReview(productId:String):Result<Boolean>
}