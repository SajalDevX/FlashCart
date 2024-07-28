package me.mrsajal.flashcart.features.stocks.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.stocks.data.RemoteStockEntity

@Serializable
data class StockApiResponseData(
    val success:Boolean,
    val stockData: RemoteStockEntity?=null,
    val message:String,
)

data class StockApiResponse(
    val data: StockApiResponseData,
    val code:HttpStatusCode,
)
