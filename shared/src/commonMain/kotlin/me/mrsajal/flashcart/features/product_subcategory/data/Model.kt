package me.mrsajal.flashcart.features.product_subcategory.data

import kotlinx.serialization.Serializable

@Serializable
data class RemoteProductSubCategoryEntity(
    val subCategoryId: String,
    val categoryId: String,
    val subCategoryName: String,
    val image: String? = null
)