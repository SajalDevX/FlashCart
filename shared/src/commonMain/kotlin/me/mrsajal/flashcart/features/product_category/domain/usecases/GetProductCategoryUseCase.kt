package me.mrsajal.flashcart.features.product_category.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_category.data.RemoteProductCategoryEntity
import me.mrsajal.flashcart.features.product_category.domain.repository.CategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProductCategoryUseCase : KoinComponent {
    private val repository by inject<CategoryRepository>()

    suspend operator fun invoke(
        offset:Int,
        limit:Int
    ): Result<List<RemoteProductCategoryEntity>> {
        return repository.getCategory(
            offset,
            limit
        )
    }
}