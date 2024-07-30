package me.mrsajal.flashcart.features.cart.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity

interface CartRepository {
    suspend fun getCartItems(
        offset: Int,
        limit: Int
    ): Result<Map<RemoteProductEntity,Int>>

    suspend fun addToCart(
        productId: String,
        qty: Int
    ): Result<Boolean>

    suspend fun updateCartItems(
        productId: String,
        qty: Int
    ): Result<Boolean>

    suspend fun removeFromCart(
        productId: String,
        qty: Int
    ): Result<Boolean>

    suspend fun removeAllFromCart(): Result<Boolean>
}