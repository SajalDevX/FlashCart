package me.mrsajal.flashcart.features.wishlist.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.wishlist.domain.repository.WishlistRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoveItemsFromWishlistUseCase : KoinComponent {
    private val repository by inject<WishlistRepository>()

    suspend operator fun invoke(
        productId: String
    ): Result<Boolean> {
        return repository.deleteProductFromWishlist(productId)
    }
}