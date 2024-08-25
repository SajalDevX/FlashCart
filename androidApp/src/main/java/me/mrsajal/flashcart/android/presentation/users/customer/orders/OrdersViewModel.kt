package me.mrsajal.flashcart.android.presentation.users.customer.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.features.order.domain.usecases.GetOrdersUseCase
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.order.domain.usecases.CancelOrderUseCase
import me.mrsajal.flashcart.features.order.domain.usecases.ReceiveOrderUseCase
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductDetailsUseCase

class OrdersViewModel(
    private val ordersUseCase: GetOrdersUseCase,
    private val getProductsDetailsUseCase: GetProductDetailsUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val receivedOrderUseCase: ReceiveOrderUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrdersUiState())
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    init {
        loadOrders()
    }

    private suspend fun fetchProduct(productId: String): RemoteProductEntity? {
        return when (val result = getProductsDetailsUseCase(productId)) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }

    private fun loadOrders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000) // Simulate network delay
            val result = ordersUseCase(limit = 10, offset = 3)
            Log.e("OrderViewModel","Data is:  ${result.message}")

            when (result) { // Adjust limit and offset as needed

                is Result.Success -> {

                    val ordersWithProducts = result.data?.map { order ->
                        val itemsWithProductDetails = order.orderItems.mapNotNull { item ->
                            val product = fetchProduct(item.productId)
                            product?.let {
                                OrderedItems(product = it, quantity = item.quantity)
                            }
                        }
                        OrderWithProductDetails(
                            orderId = order.orderId,
                            orderStatus = order.status,
                            items = itemsWithProductDetails
                        )
                    }
                    _uiState.update {
                        it.copy(
                            ordersWithProductDetails = ordersWithProducts ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(error = result.message ?: "Error fetching orders", isLoading = false)
                    }
                }
            }
        }
    }

    fun cancelOrder(orderId: String) {
        viewModelScope.launch {
            when (val result = cancelOrderUseCase(orderId)) {
                is Result.Success -> {
                    // Handle success, e.g., update UI state to reflect order cancellation
                    _uiState.update { currentState ->
                        currentState.copy(
                            ordersWithProductDetails = currentState.ordersWithProductDetails?.map {
                                if (it.orderId == orderId) it.copy(orderStatus = "Cancelled") else it
                            }
                        )
                    }
                }
                is Result.Error -> {
                    // Handle error
                    _uiState.update { it.copy(error = result.message) }
                }
            }
        }
    }

    fun receiveOrder(orderId: String) {
        viewModelScope.launch {
            when (val result = receivedOrderUseCase(orderId)) {
                is Result.Success -> {
                    // Handle success, e.g., update UI state to reflect order received
                    _uiState.update { currentState ->
                        currentState.copy(
                            ordersWithProductDetails = currentState.ordersWithProductDetails?.map {
                                if (it.orderId == orderId) it.copy(orderStatus = "Received") else it
                            }
                        )
                    }
                }
                is Result.Error -> {
                    // Handle error
                    _uiState.update { it.copy(error = result.message) }
                }
            }
        }
    }
}

data class OrderedItems(
    val product: RemoteProductEntity,
    val quantity: Int,
)

data class OrderWithProductDetails(
    val orderId: String,
    val orderStatus: String,
    val items: List<OrderedItems>
)

data class OrdersUiState(
    val isLoading: Boolean = false,
    val ordersWithProductDetails: List<OrderWithProductDetails>? = emptyList(),
    val error: String? = null
)
