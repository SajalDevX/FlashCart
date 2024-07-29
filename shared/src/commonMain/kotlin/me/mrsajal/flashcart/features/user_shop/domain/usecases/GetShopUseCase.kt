package me.mrsajal.flashcart.features.user_shop.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.user_shop.data.RemoteShopEntity
import me.mrsajal.flashcart.features.user_shop.domain.repository.ShopRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetShopUseCase : KoinComponent {
    private val repository by inject<ShopRepository>()

    suspend operator fun invoke(
    ): Result<RemoteShopEntity> {
        return repository.getShop()
    }
}