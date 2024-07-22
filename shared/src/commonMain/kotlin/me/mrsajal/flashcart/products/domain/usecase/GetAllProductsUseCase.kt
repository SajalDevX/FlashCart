package me.mrsajal.flashcart.products.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.products.domain.repository.ProductRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetAllProductsUseCase : KoinComponent {
    private val repository by inject<ProductRepository>()

    suspend operator fun invoke(
        limit: Int,
        offset: Int,
        maxPrice: Double?,
        minPrice: Double?
    ): Result<List<RemoteProductEntity>> {

        println("GetAllProductsUseCase invoked with: limit=$limit, offset=$offset, maxPrice=$maxPrice, minPrice=$minPrice")

        val result = repository.getProductsForAll(
            limit, offset, maxPrice, minPrice
        )

        // Log the result
        println("GetAllProductsUseCase result: ${result.message}")

        return withContext(Dispatchers.IO) {
            result
        }
    }
}
