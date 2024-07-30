package me.mrsajal.flashcart.features.cart.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.repository.CartRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteItemQtyFromCartUseCase : KoinComponent {
    private val repository by inject<CartRepository>()

    suspend operator fun invoke(
        productId:String,
        qty:Int
    ): Result<Boolean> {
        return repository.removeFromCart(productId, qty)
    }
}