package me.mrsajal.flashcart.features.brands.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.data.RemoteBrandEntity
import me.mrsajal.flashcart.features.brands.domain.repositpry.BrandRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class GetBrandsUseCase : KoinComponent {
    private val repository by inject<BrandRepository>()

    suspend operator fun invoke(
        offset: Int,
        limit: Int
    ): Result<List<RemoteBrandEntity>> {
        return repository.getBrands(offset = offset, limit = limit)
    }
}