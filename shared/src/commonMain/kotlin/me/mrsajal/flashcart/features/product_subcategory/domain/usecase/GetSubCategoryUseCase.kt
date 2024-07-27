package me.mrsajal.flashcart.features.product_subcategory.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_subcategory.data.RemoteProductSubCategoryEntity
import me.mrsajal.flashcart.features.product_subcategory.domain.repository.SubCategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetSubCategoryUseCase : KoinComponent {
    private val repository by inject<SubCategoryRepository>()

    suspend operator fun invoke(
        categoryId: String,
        offset: Int,
        limit: Int
    ): Result<List<RemoteProductSubCategoryEntity>> {
        return repository.getSubCategories(
            categoryId = categoryId,
            offset = offset,
            limit = limit
        )
    }
}