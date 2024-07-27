package me.mrsajal.flashcart.features.product_subcategory.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_subcategory.domain.model.SubCategoryTextParams
import me.mrsajal.flashcart.features.product_subcategory.domain.repository.SubCategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddSubCategoryUseCase : KoinComponent {
    private val repository by inject<SubCategoryRepository>()

    suspend operator fun invoke(
        subCategoryName: String,
        categoryId: String,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        val data = SubCategoryTextParams(
            subCategoryName = subCategoryName,
            categoryId = categoryId
        )
        return repository.createSubCategory(
            data,
            fileData,
            fileName
        )
    }
}