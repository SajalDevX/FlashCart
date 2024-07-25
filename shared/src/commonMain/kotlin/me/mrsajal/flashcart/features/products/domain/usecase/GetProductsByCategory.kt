package me.mrsajal.flashcart.features.products.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProductsByCategory : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        categoryId:String
    ): Result<List<RemoteProductEntity>> {
        return repository.getProductByBrand(categoryId)
    }
}