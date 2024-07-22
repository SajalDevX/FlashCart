package me.mrsajal.flashcart.products.domain.usecase

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteProductUseCase : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        userToken: String,
        productId: String,
    ): Result<Boolean> {
        return repository.deleteProduct(userToken, productId)
    }
}