package me.mrsajal.flashcart.features.product_review.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_review.domain.model.EditReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.repository.ReviewRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditReviewUseCase : KoinComponent {
    private val repository by inject<ReviewRepository>()

    suspend operator fun invoke(
        productId: String,
        caption: String,
        rating: Int
    ): Result<Boolean> {
        val editReview = EditReviewRequest(
            productId,
            caption,
            rating
        )
        val result = repository.editReview(editReview)
        return withContext(Dispatchers.IO) {
            result
        }
    }
}