package me.mrsajal.flashcart.features.cart.domain.repository

import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.model.CartListData

interface CartRepository {
    suspend fun getCartItems(
        offset: Int,
        limit: Int
    ):  Result<List<CartListData>>

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