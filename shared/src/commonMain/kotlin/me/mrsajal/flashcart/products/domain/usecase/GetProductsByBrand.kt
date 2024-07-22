package me.mrsajal.flashcart.products.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetProductsByBrand : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        brandId:String
    ): Result<List<RemoteProductEntity>> {
        return repository.getProductByBrand(brandId)
    }
}