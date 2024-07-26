package me.mrsajal.flashcart.features.product_category.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.product_category.data.RemoteProductCategoryEntity

@Serializable
data class ProductCategoryResponseData(
    val success: Boolean,
    val message: String,
    val categories: List<RemoteProductCategoryEntity> = emptyList()
)

@Serializable
data class CategoryTextParams(
    val categoryName: String
)

data class ProductCategoryResponse(
    val code: HttpStatusCode,
    val data: ProductCategoryResponseData
)