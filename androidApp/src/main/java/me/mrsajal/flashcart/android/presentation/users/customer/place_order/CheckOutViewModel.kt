package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.android.presentation.users.customer.cart.CheckoutInfo
import me.mrsajal.flashcart.android.presentation.users.customer.cart.ParcelCartListData
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.model.CartListData
import me.mrsajal.flashcart.features.order.data.OrderItems
import me.mrsajal.flashcart.features.order.domain.usecases.AddOrderUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductDetailsUseCase

class CheckOutViewModel(
    private val orderUseCase: AddOrderUseCase,
    private val getProductsUseCase: GetProductDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckOutUiState())
    val uiState: StateFlow<CheckOutUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<String>("checkoutInfo")?.let { checkoutInfoJson ->
            val checkoutInfo = Gson().fromJson(checkoutInfoJson, CheckoutInfo::class.java)
            Log.e("CheckOutViewModel", checkoutInfo.toString())
            processCheckoutInfo(checkoutInfo)
        }
    }

    private fun processCheckoutInfo(checkoutInfo: CheckoutInfo) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                productData = checkoutInfo.shippingOptions.map {
                    ParcelCartListData(it.productId, it.qty)
                },
                totalPrice = checkoutInfo.totalPrice
            )
        }
        getCheckoutItems()
    }

    private fun getCheckoutItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val itemList = mutableListOf<CartListData>()
            for (item in _uiState.value.productData ?: emptyList()) {
                val product = getProductsUseCase(item.productId)
                val data = CartListData(
                    product.data!!, item.qty
                )
                itemList.add(data)
            }
            _uiState.update { it.copy(items = itemList, isLoading = false) }
            Log.e("CheckOutViewModel", "${_uiState.value.items}")
        }
    }

    private fun placeOrder() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(1000)
            val orderItems = _uiState.value.productData?.map {
                ParcelCartListData(
                    it.productId,
                    it.qty
                ).toOrderItems()
            }?.toMutableList() ?: mutableListOf()

            val result = orderUseCase(
                quantity = 1,
                subTotal = _uiState.value.totalPrice,
                shippingCharge = _uiState.value.totalPrice / 10,
                total = _uiState.value.totalPrice,
                orderItems = orderItems
            )
            when (result) {
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }

                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, success = true) }
                }
            }
        }
    }

    fun handleAction(action: CheckOutUiAction) {
        when (action) {
            CheckOutUiAction.PlaceOrder -> placeOrder()
        }
    }
}

fun ParcelCartListData.toOrderItems(): OrderItems {
    return OrderItems(
        productId = this.productId,
        quantity = this.qty
    )
}

sealed class CheckOutUiAction {
    data object PlaceOrder : CheckOutUiAction()
}

data class CheckOutUiState(
    val items: List<CartListData> = emptyList(),
    val productData: List<ParcelCartListData>? = null,
    val totalPrice: Float = 0f,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
