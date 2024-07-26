package me.mrsajal.flashcart.features.brands.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.brands.data.RemoteBrandEntity
import me.mrsajal.flashcart.features.products.domain.model.ProductApiResponseData

@Serializable
data class BrandApiResponseData(
    val success:Boolean,
    val message:String,
    val brands:List<RemoteBrandEntity> = emptyList()
)

@Serializable
data class BrandTextParams(
    val brandName:String
)
data class BrandApiResponse(
    val code: HttpStatusCode,
    val data: BrandApiResponseData
)