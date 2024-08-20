package me.mrsajal.flashcart.android.presentation.users.customer.orders

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
            when (val result = ordersUseCase(limit = 10, offset = 10)) {
                is Result.Success -> {
                    val ordersWithProducts = result.data?.map { order ->
                        val itemsWithProductDetails = order.orderItems.mapNotNull { item ->
                            val product = fetchProduct(item.productId)
                            product?.let {
                                OrderItemStatus(product = it, quantity = item.quantity, status = order.status)
                            }
                        }
                        OrderWithProductDetails(
                            orderId = order.orderId,
                            orderStatus = order.status,
                            items = itemsWithProductDetails
                        )
                    }
                    _uiState.update { it.copy(ordersWithProductDetails = ordersWithProducts, isLoading = false) }
                }

                is Result.Error -> {
                    _uiState.update { it.copy(error = result.message, isLoading = false) }
                }
            }
        }
    }
}

data class OrderItemStatus(
    val product: RemoteProductEntity,
    val quantity: Int,
    val status: String
)

data class OrderWithProductDetails(
    val orderId: String,
    val orderStatus: String,
    val items: List<OrderItemStatus>
)

data class OrdersUiState(
    val isLoading: Boolean = false,
    val ordersWithProductDetails: List<OrderWithProductDetails>? = emptyList(),
    val error: String? = null
)
