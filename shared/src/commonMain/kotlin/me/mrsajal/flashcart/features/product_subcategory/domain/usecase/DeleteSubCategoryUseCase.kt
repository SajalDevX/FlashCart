package me.mrsajal.flashcart.features.product_subcategory.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_subcategory.domain.repository.SubCategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteSubCategoryUseCase : KoinComponent {
    private val repository by inject<SubCategoryRepository>()

    suspend operator fun invoke(
        subCategoryId: String,
        ): Result<Boolean> {
        return repository.deleteSubCategory(
            subCategoryId
        )
    }
}