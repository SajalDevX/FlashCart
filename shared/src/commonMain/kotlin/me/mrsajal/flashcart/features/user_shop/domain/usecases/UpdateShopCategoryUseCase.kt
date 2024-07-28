package me.mrsajal.flashcart.features.user_shop.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.user_shop.domain.repository.ShopRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateShopCategoryUseCase : KoinComponent {
    private val repository by inject<ShopRepository>()

    suspend operator fun invoke(
        shopCategoryId: String,
        shopCategoryName: String
    ): Result<Boolean> {
        return repository.updateShopCategory(
            shopCategoryId = shopCategoryId,
            shopCategoryName = shopCategoryName
        )
    }
}