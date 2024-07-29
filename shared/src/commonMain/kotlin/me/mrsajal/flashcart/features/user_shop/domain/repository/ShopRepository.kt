package me.mrsajal.flashcart.features.user_shop.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.user_shop.data.RemoteShopEntity
import me.mrsajal.flashcart.features.user_shop.domain.model.RemoteShopCategoryEntity


interface ShopRepository {
    suspend fun addShopCategory(shopCategoryName: String): Result<Boolean>
    suspend fun getShopCategory(offset: Int, limit: Int):Result<List<RemoteShopCategoryEntity>>
    suspend fun deleteShopCategory(shopCategoryId: String): Result<Boolean>
    suspend fun updateShopCategory(
        shopCategoryId: String,
        shopCategoryName: String
    ): Result<Boolean>

    suspend fun addShop(
        shopName: String,
        shopCategoryId: String,
    ): Result<Boolean>
    suspend fun getShop():Result<RemoteShopEntity>
}