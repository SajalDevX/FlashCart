package me.mrsajal.flashcart.android.presentation.users.customer.wishlist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.wishlist.domain.usecases.GetWishListItemsUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.RemoveItemsFromWishlistUseCase


class WishlistViewModel(
    private val getWishlistUseCase: GetWishListItemsUseCase,
    private val removeWishlistUseCase: RemoveItemsFromWishlistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WishListUiState())
    val uiState: StateFlow<WishListUiState> = _uiState.asStateFlow()

    init {
        getWishListItems()
    }

     fun getWishListItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(500)
            try {
                val result = getWishlistUseCase()
                _uiState.value = when (result) {
                    is Result.Error -> {
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    is Result.Success -> {
                        _uiState.value.copy(
                            isLoading = false,
                            wishList = result.data ?: emptyList()
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "An error occurred: ${e.message}"
                )
            }
        }
    }

    private fun removeItemFromWishlist(productId: String) {
        viewModelScope.launch {
            when (val result = removeWishlistUseCase(productId)) {
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                is Result.Success -> {
                    val updatedList = _uiState.value.wishList.filter { it.productId != productId }
                    _uiState.value = _uiState.value.copy(
                        wishList = updatedList
                    )
                    getWishListItems()
                }
            }
        }
    }

    fun onUiAction(event: WishlistEvent) {
        when (event) {
            is WishlistEvent.RemoveItem -> removeItemFromWishlist(event.id)
        }
    }
}

data class WishListUiState(
    val wishList: List<RemoteProductEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface WishlistEvent {
    data class RemoveItem(val id: String) : WishlistEvent
}
