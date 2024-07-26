package me.mrsajal.flashcart.features.brands.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.domain.repositpry.BrandRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteBrandUseCase : KoinComponent {
    private val repository by inject<BrandRepository>()

    suspend operator fun invoke(
        brandId: String,
    ): Result<Boolean> {
        return repository.deleteBrand(brandId)
    }
}