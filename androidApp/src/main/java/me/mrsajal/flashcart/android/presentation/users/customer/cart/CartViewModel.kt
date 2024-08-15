package me.mrsajal.flashcart.android.presentation.users.customer.cart

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.model.CartListData
import me.mrsajal.flashcart.features.cart.domain.usecases.*
import me.mrsajal.flashcart.features.profile.data.UserAddress
import me.mrsajal.flashcart.features.profile.domain.usecases.GetProfileUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.AddItemsToWishlistUseCase
import kotlinx.parcelize.Parcelize


class CartViewModel(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val increaseCartItemsUseCase: IncreaseItemQtyInCartUseCase,
    private val decreaseCartItemsUseCase: DeleteItemQtyFromCartUseCase,
    private val removeAllItemsFromCartUseCase: DeleteAllItemsFromCart,
    private val userProfileUseCase: GetProfileUseCase,
    private val wishlistUseCase: AddItemsToWishlistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartScreenUiState())
    val uiState: StateFlow<CartScreenUiState> = _uiState.asStateFlow()

    init {
        getCartItems()
        getAddressData()
    }

    private fun getCartItems() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            val result = runCatching { getCartItemsUseCase(limit = 4, offset = 5) }
            _uiState.update {
                when (val data = result.getOrNull()) {
                    is Result.Success -> it.copy(
                        cartItems = data.data ?: emptyList(),
                        isLoading = false,
                        isEmpty = data.data?.isEmpty() ?: true
                    )

                    is Result.Error -> it.copy(
                        error = data.message,
                        isLoading = false
                    )

                    else -> it.copy(isLoading = false)
                }
            }
            createCheckoutInfo()
        }
    }

    private fun updateCartState(
        result: Result<Boolean>?,
        updatedItems: List<CartListData>
    ) {
        _uiState.update {
            when (result) {
                is Result.Success -> {
                    val selectedTotalPrice =
                        calculateSelectedTotalPrice(updatedItems, it.selectedItems)
                    val selectedDiscountedPrice =
                        calculateSelectedDiscountedPrice(updatedItems, it.selectedItems)
                    it.copy(
                        cartItems = updatedItems,
                        selectedTotalPrice = selectedTotalPrice,
                        selectedDiscountedPrice = selectedDiscountedPrice,
                        isLoading = false
                    )
                }

                is Result.Error -> it.copy(
                    error = result.message,
                    isLoading = false
                )

                else -> it.copy(isLoading = false)
            }
        }
        createCheckoutInfo()
    }

    private fun createCheckoutInfo() {
        _uiState.update { currentState ->
            val selectedItems = currentState.selectedItems
            val parcelCartItems = currentState.cartItems
                .filter { it.product.productId in selectedItems }
                .map { it.toParcelCartListData() }

            val checkoutInfo = CheckoutInfo(
                shippingOptions = parcelCartItems,
                address = currentState.selectedAddress?.toParcelAddressData()
                    ?: return@update currentState,
                totalPrice = currentState.selectedTotalPrice.toFloat(),
            )
            currentState.copy(checkoutInfo = checkoutInfo)
        }
    }



    private fun increaseItemQtyInCart(id: String) {
        viewModelScope.launch {
            val result = runCatching { increaseCartItemsUseCase(id, 1) }.getOrNull()
            val updatedItems = _uiState.value.cartItems.map { item ->
                if (item.product.productId == id) item.copy(qty = item.qty + 1) else item
            }
            updateCartState(result, updatedItems)
        }
    }

    private fun decreaseItemQtyInCart(id: String) {
        viewModelScope.launch {
            val result = runCatching { decreaseCartItemsUseCase(id, 1) }.getOrNull()
            val updatedItems = _uiState.value.cartItems.mapNotNull { item ->
                if (item.product.productId == id) item.copy(qty = item.qty - 1)
                    .takeIf { it.qty > 0 }
                else item
            }
            updateCartState(result, updatedItems)
        }
    }

    private fun removeAllItemsFromCart() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = runCatching { removeAllItemsFromCartUseCase() }.getOrNull()
            _uiState.update {
                when (result) {
                    is Result.Success -> it.copy(
                        cartItems = emptyList(),
                        isEmpty = true,
                        isLoading = false
                    )

                    is Result.Error -> it.copy(
                        error = result.message,
                        isLoading = false
                    )

                    else -> it.copy(isLoading = false)
                }
            }
            createCheckoutInfo()
        }
    }

    private fun toggleSelectAll() {
        viewModelScope.launch {
            val selectAll = !_uiState.value.selectAll
            val newSelectedItems = if (selectAll) {
                _uiState.value.cartItems.map { it.product.productId }.toSet()
            } else {
                emptySet()
            }
            val selectedTotalPrice =
                calculateSelectedTotalPrice(_uiState.value.cartItems, newSelectedItems)
            val selectedDiscountedPrice =
                calculateSelectedDiscountedPrice(_uiState.value.cartItems, newSelectedItems)
            _uiState.update {
                it.copy(
                    selectedItems = newSelectedItems,
                    selectAll = selectAll,
                    selectedTotalPrice = selectedTotalPrice,
                    selectedDiscountedPrice = selectedDiscountedPrice
                )
            }
            createCheckoutInfo()
        }
    }

    private fun selectItem(id: String, isSelected: Boolean) {
        viewModelScope.launch {
            val newSelectedItems = _uiState.value.selectedItems.toMutableSet().apply {
                if (isSelected) add(id) else remove(id)
            }
            val selectedTotalPrice =
                calculateSelectedTotalPrice(_uiState.value.cartItems, newSelectedItems)
            val selectedDiscountedPrice =
                calculateSelectedDiscountedPrice(_uiState.value.cartItems, newSelectedItems)
            _uiState.update {
                it.copy(
                    selectedItems = newSelectedItems,
                    selectedTotalPrice = selectedTotalPrice,
                    selectedDiscountedPrice = selectedDiscountedPrice
                )
            }
            createCheckoutInfo()
        }
    }

    private fun calculateSelectedTotalPrice(
        cartItems: List<CartListData>,
        selectedItems: Set<String>
    ): Int {
        return cartItems.filter { it.product.productId in selectedItems }
            .sumOf { it.product.price.toInt() * it.qty }
    }

    private fun calculateSelectedDiscountedPrice(
        cartItems: List<CartListData>,
        selectedItems: Set<String>
    ): Int {
        return cartItems.filter { it.product.productId in selectedItems }
            .sumOf { it.product.discountPrice!!.toInt() * it.qty }
    }

    private fun getAddressData() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            val result = runCatching { userProfileUseCase() }.getOrNull()
            _uiState.update {
                when (result) {
                    is Result.Success -> {
                        val addresses = result.data?.userDetails?.addresses ?: emptyList()
                        val selectedAddress = addresses.firstOrNull()
                        it.copy(
                            address = addresses,
                            selectedAddress = selectedAddress,
                            isLoading = false
                        )
                    }

                    is Result.Error -> it.copy(
                        error = result.message,
                        isLoading = false
                    )

                    else -> it.copy(isLoading = false)
                }
            }
            createCheckoutInfo()
        }
    }

    private fun selectAddress(address: UserAddress) {
        _uiState.update { it.copy(selectedAddress = address) }
    }

    private fun addToWishlist(productId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = runCatching { wishlistUseCase(productId) }.getOrNull()
            _uiState.update {
                when (result) {
                    is Result.Success -> {
                        Log.d("CartViewModel", "Item added to wishlist: $productId")
                        it.copy(isLoading = false)
                    }

                    is Result.Error -> it.copy(
                        error = result.message,
                        isLoading = false
                    )

                    else -> it.copy(isLoading = false)
                }
            }
        }
    }

    fun handleAction(action: CartUiAction) {
        when (action) {
            is CartUiAction.FetchCartItems -> getCartItems()
            is CartUiAction.IncreaseItemCount -> increaseItemQtyInCart(action.id)
            is CartUiAction.ReduceItemCount -> decreaseItemQtyInCart(action.id)
            is CartUiAction.RemoveItem -> {}
            is CartUiAction.SelectItem -> selectItem(action.id, action.isSelected)
            is CartUiAction.SelectAddress -> selectAddress(action.address)
            is CartUiAction.ToggleSelectAll -> toggleSelectAll()
            is CartUiAction.DeleteAllItems -> removeAllItemsFromCart()
            is CartUiAction.AddToWishlist -> addToWishlist(action.id)
        }
    }
}

data class CartScreenUiState(
    val address: List<UserAddress>? = emptyList(),
    val selectedAddress: UserAddress? = null,
    val cartItems: List<CartListData> = emptyList(),
    val totalPrice: Double = 0.0,
    val selectedTotalPrice: Int = 0,
    val selectedDiscountedPrice: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmpty: Boolean = false,
    val checkoutInfo: CheckoutInfo? = null,
    val selectedItems: Set<String> = emptySet(),
    val selectAll: Boolean = true,
    val isCheckoutButtonEnabled: Boolean = true
)

@Parcelize
data class CheckoutInfo(
    val shippingOptions: List<ParcelCartListData>, // Use ParcelCartListData here
    val address: ParcelAddressData,
    val totalPrice: Float,
) : Parcelable

@Parcelize
data class ParcelAddressData(
    val fatherName: String,
    val motherName: String,
    val pin: String,
    val mobileNumber: String,
    val otherMobileNumber: String? = null,
    val city: String,
    val road: String,
    val state: String,
) : Parcelable

@Parcelize
data class ParcelCartListData(
    val productId: String,
    val qty: Int
) : Parcelable

internal fun UserAddress.toParcelAddressData(): ParcelAddressData {
    return ParcelAddressData(
        fatherName, motherName, pin, mobileNumber, otherMobileNumber, city, road, state
    )
}
internal fun ParcelAddressData.toUserAddress(): UserAddress {
    return UserAddress(
        fatherName, motherName, pin, mobileNumber, otherMobileNumber, city, road, state
    )
}

internal fun CartListData.toParcelCartListData(): ParcelCartListData {
    return ParcelCartListData(
        product.productId,
        qty
    )
}

sealed class CartUiAction {
    data object FetchCartItems : CartUiAction()
    data class ReduceItemCount(val id: String) : CartUiAction()
    data class IncreaseItemCount(val id: String) : CartUiAction()
    data class RemoveItem(val id: String) : CartUiAction()
    data class AddToWishlist(val id: String) : CartUiAction()
    data class SelectItem(val id: String, val isSelected: Boolean) : CartUiAction()
    data class SelectAddress(val address: UserAddress) : CartUiAction()
    data object DeleteAllItems : CartUiAction()
    data object ToggleSelectAll : CartUiAction()
}
