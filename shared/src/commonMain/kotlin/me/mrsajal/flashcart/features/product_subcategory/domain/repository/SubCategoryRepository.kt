package me.mrsajal.flashcart.features.product_subcategory.domain.repository

import me.mrsajal.flashcart.features.product_subcategory.data.RemoteProductSubCategoryEntity
import me.mrsajal.flashcart.features.product_subcategory.domain.model.SubCategoryTextParams
import me.mrsajal.flashcart.common.utils.Result


interface SubCategoryRepository {
    suspend fun createSubCategory(
        subCategoryData: SubCategoryTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean>

    suspend fun deleteSubCategory(subCategoryId: String): Result<Boolean>
    suspend fun getSubCategories(
        categoryId: String,
        limit: Int,
        offset: Int
    ): Result<List<RemoteProductSubCategoryEntity>>
}