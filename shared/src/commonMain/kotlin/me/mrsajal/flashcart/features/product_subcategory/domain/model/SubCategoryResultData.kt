package me.mrsajal.flashcart.features.product_subcategory.domain.model

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.product_subcategory.data.RemoteProductSubCategoryEntity

@Serializable
data class ProductSubCategoryResponseData(
    val success: Boolean,
    val message:String,
    val subCategories : List<RemoteProductSubCategoryEntity> = emptyList(),
)
@Serializable
data class SubCategoryTextParams(
    val categoryId:String,
    val subCategoryName:String,
)
data class ProductSubCategoryResponse(
    val data:ProductSubCategoryResponseData,
    val code:HttpStatusCode
)