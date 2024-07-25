package me.mrsajal.flashcart.features.product_review.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_review.data.RemoteReviewEntity
import me.mrsajal.flashcart.features.product_review.domain.repository.ReviewRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProductReviewUseCase : KoinComponent {
    private val repository by inject<ReviewRepository>()

    suspend operator fun invoke(
        productId: String
    ): Result<List<RemoteReviewEntity>> {

        val result = repository.getAllReviews(productId )
        return withContext(Dispatchers.IO) {
            result
        }
    }
}