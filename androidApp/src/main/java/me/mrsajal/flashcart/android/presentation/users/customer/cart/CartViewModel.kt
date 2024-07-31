package me.mrsajal.flashcart.android.presentation.users.customer.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.model.CartListData
import me.mrsajal.flashcart.features.cart.domain.usecases.DeleteAllItemsFromCart
import me.mrsajal.flashcart.features.cart.domain.usecases.DeleteItemQtyFromCartUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.GetCartItemsUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.IncreaseItemQtyInCartUseCase
import me.mrsajal.flashcart.features.profile.data.UserAddress

class CartViewModel(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val increaseCartItemsUseCase: IncreaseItemQtyInCartUseCase,
    private val decreaseCartItemsUseCase: DeleteItemQtyFromCartUseCase,
    private val removeAllItemsFromCartUse: DeleteAllItemsFromCart
) : ViewModel() {
    var uiState by mutableStateOf(CartScreenUiState())
        private set

    private fun getCartItems() {
        viewModelScope.launch {
            delay(500)
            try {
                uiState = uiState.copy(isLoading = true)
                val result = getCartItemsUseCase(limit = 4, offset = 5)
                when (result) {
                    is Result.Error -> {
                        uiState = uiState.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Result.Success -> {
                        val itemData = result.data
                        if (itemData != null) {
                            uiState = uiState.copy(
                                cartItems = itemData,
                                isLoading = false,
                                isEmpty = itemData.isEmpty()
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message, isLoading = false)
            }
        }
    }
    private fun removeAllItemsFromCart() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = removeAllItemsFromCartUse()
                uiState = when (result) {
                    is Result.Error -> {
                        uiState.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Result.Success -> {
                        uiState.copy(
                            cartItems = emptyList(),
                            isEmpty = true,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    private fun increaseItemQtyInCart(id: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val result = increaseCartItemsUseCase(id, 1)
                when (result) {
                    is Result.Error -> {
                        uiState = uiState.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Result.Success -> {
                        val updatedItems = uiState.cartItems.map { item ->
                            if (item.product.productId == id) {
                                item.copy(qty = item.qty + 1)
                            } else {
                                item
                            }
                        }

                        val newTotalPrice = updatedItems.sumOf { it.product.price * it.qty }
                        val totalPrice = uiState.totalPrice + newTotalPrice
                        uiState = uiState.copy(
                            cartItems = updatedItems,
                            totalPrice = totalPrice,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    private fun decreaseItemQtyInCart(id: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val result = decreaseCartItemsUseCase(id, 1)
                when (result) {
                    is Result.Error -> {
                        uiState = uiState.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }

                    is Result.Success -> {
                        val updatedItems = uiState.cartItems.map { item ->
                            if (item.product.productId == id) {
                                item.copy(qty = item.qty - 1)
                            } else {
                                item
                            }
                        }

                        val newTotalPrice = updatedItems.sumOf { it.product.price * it.qty }
                        val totalPrice = uiState.totalPrice - newTotalPrice
                        uiState = uiState.copy(
                            cartItems = updatedItems,
                            totalPrice = totalPrice,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }
    private fun toggleSelectAll() {
        viewModelScope.launch {
            val selectAll = !uiState.selectAll
            val newSelectedItems = if (selectAll) {
                uiState.cartItems.map { it.product.productId }.toSet()
            } else {
                emptySet()
            }
            uiState = uiState.copy(
                selectedItems = newSelectedItems,
                selectAll = selectAll
            )
        }
    }
    private fun selectItem(id: String) {
        viewModelScope.launch {
            val newSelectedItems = uiState.selectedItems.toMutableSet().apply {
                if (contains(id)) remove(id) else add(id)
            }
            val selectAll = newSelectedItems.size == uiState.cartItems.size
            uiState = uiState.copy(
                selectedItems = newSelectedItems,
                selectAll = selectAll
            )
        }
    }
    fun handleAction(action: CartUiAction) {
        when (action) {
            is CartUiAction.FetchCartItems -> getCartItems()
            is CartUiAction.IncreaseItemCount -> increaseItemQtyInCart(action.id)
            is CartUiAction.ReduceItemCount -> decreaseItemQtyInCart(action.id)
            is CartUiAction.RemoveItem -> CartUiAction.RemoveItem(action.id)
            is CartUiAction.Checkout -> CartUiAction.Checkout(action.items, action.totalPrice)
            is CartUiAction.SelectItem -> selectItem(action.id)
            is CartUiAction.ToggleSelectAll -> toggleSelectAll()
            is CartUiAction.DeleteAllItems -> removeAllItemsFromCart()
        }
    }
}

data class CartScreenUiState(
    val cartItems: List<CartListData> = emptyList(),
    val totalPrice: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false,
    val checkoutInfo: CheckoutInfo? = null,
    val selectedItems: Set<String> = emptySet(),
    val selectAll: Boolean = true,
    val isCheckoutButtonEnabled: Boolean = true
)


data class CheckoutInfo(
    val shippingOptions: List<CartListData>,
    val address: UserAddress,
    val totalPrice: Float,
    val paymentType: String,
)

sealed class CartUiAction {
    data object FetchCartItems : CartUiAction()
    data class ReduceItemCount(val id: String) : CartUiAction()
    data class IncreaseItemCount(val id: String) : CartUiAction()
    data class RemoveItem(val id: String) : CartUiAction()
    data class Checkout(val items: List<CartListData>, val totalPrice: Float) : CartUiAction()
    data class SelectItem(val id: String) : CartUiAction()
    data object DeleteAllItems : CartUiAction()
    data object ToggleSelectAll : CartUiAction()
}