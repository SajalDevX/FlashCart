package me.mrsajal.flashcart.features.user_shop.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.user_shop.domain.repository.ShopRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddShopCategoryUseCase : KoinComponent {
    private val repository by inject<ShopRepository>()

    suspend operator fun invoke(
        shopCategoryName: String
    ): Result<Boolean> {
        return repository.addShopCategory(shopCategoryName)
    }
}