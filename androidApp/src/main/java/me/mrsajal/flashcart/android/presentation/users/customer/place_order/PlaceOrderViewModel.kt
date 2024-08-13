package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import me.mrsajal.flashcart.features.cart.domain.model.CartListData

data class PlaceOrderUiState(
    val productData: List<CartListData>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)