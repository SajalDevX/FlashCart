package me.mrsajal.flashcart.features.brands.domain.repositpry

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.data.RemoteBrandEntity
import me.mrsajal.flashcart.features.brands.domain.model.BrandTextParams

interface BrandRepository {
    suspend fun createBrands(
        brandData: BrandTextParams,
        fileData: ByteArray,
        fileName: String
    ): Result<Boolean>

    suspend fun getBrands(
        offset: Int,
        limit: Int
    ): Result<List<RemoteBrandEntity>>

    suspend fun deleteBrand(
        brandId: String
    ): Result<Boolean>
}