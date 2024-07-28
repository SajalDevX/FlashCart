package me.mrsajal.flashcart.features.user_shop.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.user_shop.domain.model.RemoteShopCategoryEntity
import me.mrsajal.flashcart.features.user_shop.domain.repository.ShopRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetShopCategoryUseCase : KoinComponent {
    private val repository by inject<ShopRepository>()

    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): Result<List<RemoteShopCategoryEntity>> {
        return repository.getShopCategory(offset = offset, limit = limit)
    }
}