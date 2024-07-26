package me.mrsajal.flashcart.features.product_category.data

import kotlinx.serialization.Serializable
import me.mrsajal.flashcart.features.product_subcategory.data.RemoteProductSubCategoryEntity

@Serializable
data class RemoteProductCategoryEntity(
    val productCategoryId: String,
    val categoryName: String,
    val subCategories: MutableList<RemoteProductSubCategoryEntity> = mutableListOf(),
    val image: String? = null
)