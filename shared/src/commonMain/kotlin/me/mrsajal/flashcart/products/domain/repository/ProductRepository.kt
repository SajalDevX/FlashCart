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
        productId: String,
        updateProduct: UpdateProductRequest
    ): Result<Boolean>

    suspend fun getProductById(
        productId: String
    ):Result<RemoteProductEntity>

    suspend fun uploadImage(
        productId: String,
        imageFileName: String,
        imageBytes: ByteArray
    ): Result<Boolean>

    suspend fun deleteProduct(
        productId: String
    ): Result<Boolean>

    suspend fun getProductDetail(
        productId: String
    ): Result<RemoteProductEntity>

    suspend fun getProductsForAll(
        limit: Int,
        offset: Int,
        maxPrice: Double?,
        minPrice: Double?
    ): Result<List<RemoteProductEntity>>

    suspend fun getProductByCategory(
        categoryId: String
    ):Result<List<RemoteProductEntity>>

    suspend fun getProductBySubCategory(
        subCategoryId: String
    ):Result<List<RemoteProductEntity>>

    suspend fun getProductByBrand(
        brandId: String
    ):Result<List<RemoteProductEntity>>
}