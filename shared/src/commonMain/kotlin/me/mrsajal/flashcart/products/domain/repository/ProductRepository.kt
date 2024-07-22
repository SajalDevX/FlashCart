package me.mrsajal.flashcart.products.domain.repository

import me.mrsajal.flashcart.products.data.AddProductRequest
import me.mrsajal.flashcart.products.data.UpdateProductRequest
import me.mrsajal.flashcart.products.domain.model.ProductApiResponse
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity

interface ProductRepository {
    suspend fun addProduct(
        addProduct: AddProductRequest,
    ): Result<Boolean>

    suspend fun updateProduct(
        userToken: String,
        productId: String,
        updateProduct: UpdateProductRequest
    ): Result<Boolean>

    suspend fun getProductById(
        userToken: String,
        productId: String
    ):Result<RemoteProductEntity>

    suspend fun uploadImage(
        userToken: String,
        productId: String,
        imageFileName: String,
        imageBytes: ByteArray
    ): Result<Boolean>

    suspend fun deleteProduct(
        userToken: String,
        productId: String
    ): Result<Boolean>

    suspend fun getProductDetail(
        userToken: String,
        productId: String
    ): Result<RemoteProductEntity>

    suspend fun getProductsForAll(
        userToken: String,
        limit: Int,
        offset: Int,
        maxPrice: Double?,
        minPrice: Double?,
        categoryId: String?,
        subCategoryId: String?,
        brandId: String?,
    ): Result<List<RemoteProductEntity>>
}