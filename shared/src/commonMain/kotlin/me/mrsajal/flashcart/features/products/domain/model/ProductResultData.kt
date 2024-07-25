package me.mrsajal.flashcart.features.products.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable


@Serializable
data class RemoteProductEntity(
    val productId: String ,
    val userId:String,
    val categoryId: String,
    val subCategoryId: String?,
    val brandId: String?,
    val productName: String,
    val productCode: String?,
    val productQuantity: Int,
    val productDetail: String,
    val price: Double,
    val discountPrice: Double?,
    val videoLink: String?,
    val hotDeal: String?,
    val ratingId:List<String> = emptyList(),
    val buyOneGetOne: String?,
    val images:List<String>?= emptyList()
)
@Serializable
data class ProductApiResponseData(
    val success: Boolean,
    val product: RemoteProductEntity? = null,
    val allProducts: List<RemoteProductEntity> = emptyList(),
    val message: String? = null
)

data class ProductApiResponse(
    val code:HttpStatusCode,
    val data: ProductApiResponseData
)
