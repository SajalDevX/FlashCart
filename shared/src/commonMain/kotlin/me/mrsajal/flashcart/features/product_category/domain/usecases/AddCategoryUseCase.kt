package me.mrsajal.flashcart.features.product_category.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.product_category.domain.model.CategoryTextParams
import me.mrsajal.flashcart.features.product_category.domain.repository.CategoryRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddCategoryUseCase : KoinComponent {
    private val repository by inject<CategoryRepository>()

    suspend operator fun invoke(
        categoryName: CategoryTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        return repository.createCategory(
            categoryName,
            fileData,
            fileName
        )
    }
}