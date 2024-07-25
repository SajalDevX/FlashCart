package me.mrsajal.flashcart.features.products.data

import kotlinx.serialization.Serializable


@Serializable
data class AddProductRequest(
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
    val buyOneGetOne: String?,
    val images: List<String> = emptyList(),
)

@Serializable
data class UpdateProductRequest(
    val categoryId: String? = null,
    val subCategoryId: String? = null,
    val brandId: String? = null,
    val productName: String? = null,
    val productCode: String? = null,
    val productQuantity: Int? = null,
    val productDetail: String? = null,
    val price: Double? = null,
    val discountPrice: Double? = null,
    val videoLink: String? = null,
    val hotDeal: String? = null,
    val buyOneGetOne: String? = null,
    val images: List<String> = emptyList(),
)
