package me.mrsajal.flashcart.android.presentation.wishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.wishlist.domain.usecases.GetWishListItemsUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.RemoveItemsFromWishlistUseCase


class WishlistViewModel(
    private val getWishlistUseCase: GetWishListItemsUseCase,
    private val removeWishlistUseCase: RemoveItemsFromWishlistUseCase
) : ViewModel() {
    var uiState by mutableStateOf(WishListUiState())
        private set
    init {
        getWishListItems()
    }
     fun getWishListItems() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                delay(500)
                val result = getWishlistUseCase()
                uiState = when (result) {
                    is Result.Error -> {
                        uiState.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }

                    is Result.Success -> {
                        uiState.copy(
                            isLoading = false,
                            wishList = result.data ?: emptyList()
                        )
                    }
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "An error occurred: ${e.message}"
                )
            }
        }
    }
    private fun removeItemFromWishlist(productId: String) {
        viewModelScope.launch {
            val result = removeWishlistUseCase(productId)
            when (result) {
                is Result.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                is Result.Success -> {
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
sealed interface WishlistEvent{
    data class RemoveItem(val id: String) : WishlistEvent
}