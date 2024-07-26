package me.mrsajal.flashcart.features.brands.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.domain.model.BrandTextParams
import me.mrsajal.flashcart.features.brands.domain.repositpry.BrandRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AddBrandUseCase : KoinComponent {
    private val repository by inject<BrandRepository>()

    suspend operator fun invoke(
        brandData: BrandTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean> {
        return repository.createBrands(
            brandData,
            fileData,
            fileName
        )
    }
}