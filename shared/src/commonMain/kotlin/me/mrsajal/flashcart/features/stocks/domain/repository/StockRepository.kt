package me.mrsajal.flashcart.features.stocks.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.stocks.data.RemoteStockEntity

interface StockRepository {
    suspend fun addStocks(
        productId: String,
        shopId: String,
        qty: Int
    ): Result<Boolean>

    suspend fun reduceStocks(
        productId: String,
        shopId: String,
        qty: Int
    ): Result<Boolean>

    suspend fun getStocks(
        productId: String,
        shopId: String
    ): Result<RemoteStockEntity>
}