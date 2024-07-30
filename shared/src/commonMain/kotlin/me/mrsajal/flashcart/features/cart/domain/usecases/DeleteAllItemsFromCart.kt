package me.mrsajal.flashcart.features.cart.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.repository.CartRepository
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteAllItemsFromCart : KoinComponent {
    private val repository by inject<CartRepository>()

    suspend operator fun invoke(): Result<Boolean> {
        return repository.removeAllFromCart()
    }
}