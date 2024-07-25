package me.mrsajal.flashcart.features.brands.domain.repositpry

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.brands.data.RemoteBrandEntity

interface BrandRepository {
    suspend fun createBrands(brandName: String): Result<Boolean>
    suspend fun getBrands(limit:Int,offset:Int): Result<List<RemoteBrandEntity>>
    suspend fun updateBrand(brandId: String, brandName: String): Result<Boolean>
    suspend fun deleteBrand(brandId: String): Result<Boolean>
}