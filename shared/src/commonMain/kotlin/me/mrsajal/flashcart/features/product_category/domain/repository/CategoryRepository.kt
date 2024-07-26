package me.mrsajal.flashcart.features.product_category.domain.repository

import me.mrsajal.flashcart.features.product_category.data.RemoteProductCategoryEntity
import me.mrsajal.flashcart.features.product_category.domain.model.CategoryTextParams
import me.mrsajal.flashcart.common.utils.Result
interface CategoryRepository {
    suspend fun createCategory(
        categoryData: CategoryTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean>

    suspend fun deleteCategory(
        categoryId: String,
        userToken:String
    ): Result<Boolean>

    suspend fun getCategory(
        userToken:String,
        limit: Int,
        offset: Int
    ): Result<List<RemoteProductCategoryEntity>>
}