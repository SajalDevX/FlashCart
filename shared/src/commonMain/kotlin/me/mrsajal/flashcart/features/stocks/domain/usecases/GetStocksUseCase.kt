package me.mrsajal.flashcart.features.stocks.domain.usecases

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.stocks.data.RemoteStockEntity
import me.mrsajal.flashcart.features.stocks.domain.repository.StockRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetStocksUseCase : KoinComponent {
    private val repository by inject<StockRepository>()

    suspend operator fun invoke(
        productId: String,
        shopId: String,
    ): Result<RemoteStockEntity> {
        return repository.getStocks(productId = productId, shopId = shopId)
    }
}