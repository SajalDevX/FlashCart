package me.mrsajal.flashcart.features.cart.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.model.CartListData
import me.mrsajal.flashcart.features.cart.domain.repository.CartRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCartItemsUseCase : KoinComponent {
    private val repository by inject<CartRepository>()

    suspend operator fun invoke(
        offset:Int,
        limit:Int
    ): Result<List<CartListData>>{

        val result = repository.getCartItems(offset, limit)
        println("GetCartItemsUseCase invoked with: limit=$limit, offset=$offset, items: ${result.data}")

        return result

    }
}