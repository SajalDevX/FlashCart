package me.mrsajal.flashcart.features.wishlist.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity

interface WishlistRepository{
    suspend fun getWishlistItems():Result<List<RemoteProductEntity>>
    suspend fun deleteProductFromWishlist(productId: String):Result<Boolean>
    suspend fun addToWishlist(productId: String):Result<Boolean>
}