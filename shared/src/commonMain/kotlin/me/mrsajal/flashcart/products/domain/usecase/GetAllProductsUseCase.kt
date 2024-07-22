package me.mrsajal.flashcart.products.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class GetAllProductsUseCase : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        userToken: String,
        limit: Int,
        offset: Int,
        maxPrice: Double?,
        minPrice: Double?,
        categoryId: String?,
        subCategoryId: String?,
        brandId: String
    ): Result<List<RemoteProductEntity>> {
        return repository.getProductsForAll(
            userToken, limit, offset, maxPrice, minPrice, categoryId, subCategoryId, brandId
        )
    }
}