package me.mrsajal.flashcart.features.product_review.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_review.domain.model.AddReviewRequest
import me.mrsajal.flashcart.features.product_review.domain.repository.ReviewRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddReviewUseCase : KoinComponent {
    private val repository by inject<ReviewRepository>()

    suspend operator fun invoke(
        productId: String,
        caption:String,
        rating: Int
    ): Result<Boolean> {
        val addReview = AddReviewRequest(
            productId, caption, rating
        )
        val result = repository.addReview(addReview)
        return withContext(Dispatchers.IO) {
            result
        }
    }
}