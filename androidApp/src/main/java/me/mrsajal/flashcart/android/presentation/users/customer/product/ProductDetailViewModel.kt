package me.mrsajal.flashcart.android.presentation.users.customer.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.usecases.AddItemToCartUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.GetCartItemsUseCase
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductByIdUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsByBrand
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsBySubCategory
import me.mrsajal.flashcart.features.wishlist.domain.usecases.AddItemsToWishlistUseCase


class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val fetchProductDataUseCase: GetProductByIdUseCase,
    private val getFeedProducts: GetProductsBySubCategory,
    private val getProductsByBrand: GetProductsByBrand,
    private val addItemsToCart: AddItemToCartUseCase,
    private val addItemsToWishlistUseCase: AddItemsToWishlistUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase
) : ViewModel() {
    var uiState by mutableStateOf(ProductDetailUiState())
        private set
    private var currentId: String? = null
    private var brandId: String? = null
    private var subCategoryId: String? = null

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            if (productId.isNotEmpty()) {
                currentId = productId
                fetchProductData(productId)
//                checkIfItemInCart(productId)
            }
        }
    }

//    private fun checkIfItemInCart(id: String) {
//        viewModelScope.launch {
//            val limit = 20
//            var offset = 0
//            var found = false
//
//            while (true) {
//                val cartItems = getCartItemsUseCase(limit, offset)
//                if (cartItems.data?.isEmpty() == true) break
//
//                if (cartItems.data?.any { it.product.productId == id } == true) {
//                    found = true
//                    break
//                }
//
//                offset += limit
//            }
//            uiState = uiState.copy(isItemInCart = found)
//        }
//    }

    private fun fetchProductData(id: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            val result = fetchProductDataUseCase(id)
            brandId = result.data?.brandId
            subCategoryId = result.data?.subCategoryId
            uiState = when (result) {
                is Result.Error -> {
                    uiState.copy(isLoading = false, error = result.message)
                }

                is Result.Success -> {
                    uiState.copy(isLoading = false, product = result.data)
                }
            }
        }
    }

    private fun addToCart(qty: Int) {
        viewModelScope.launch {
            val result = addItemsToCart(productId = currentId!!, qty = qty)
            uiState = when (result) {
                is Result.Error -> {
                    uiState.copy(error = result.message)
                }

                is Result.Success -> {
                    uiState.copy(success = result.data)
                }
            }
        }
    }

    private fun addToWishList() {
        viewModelScope.launch {
            val result = addItemsToWishlistUseCase(productId = currentId!!)
            uiState = when (result) {
                is Result.Error -> {
                    uiState.copy(error = result.message)
                }

                is Result.Success -> {
                    uiState.copy(success = result.data, isWishListed = true)
                }
            }
        }
    }

    private fun getSubCategoryProducts() {
        viewModelScope.launch {
            val result = getFeedProducts(subCategoryId!!)
            uiState = when (result) {
                is Result.Error -> {
                    uiState.copy(error = result.message)
                }

                is Result.Success -> {
                    uiState.copy(subCategoryProducts = result.data)
                }
            }
        }
    }

    private fun getSimilarBrandItems() {
        viewModelScope.launch {
            val result = getProductsByBrand(brandId!!)
            uiState = when (result) {
                is Result.Error -> {
                    uiState.copy(error = result.message)
                }

                is Result.Success -> {
                    uiState.copy(similarBrandItems = result.data)
                }
            }
        }
    }

    fun onUiEvent(action: ProductDetailEvent) {
        when (action) {
            is ProductDetailEvent.AddToCart -> addToCart(action.qty)
            is ProductDetailEvent.AddToWishlist -> {}
            is ProductDetailEvent.BuyNow -> addToWishList()
            ProductDetailEvent.FetchData -> {
                fetchProductData(currentId!!)
//                getSubCategoryProducts()
//                getSimilarBrandItems()
            }
        }
    }

}

sealed class ProductDetailEvent {
    data object FetchData : ProductDetailEvent()
    data class AddToCart(val qty: Int) : ProductDetailEvent()
    data class BuyNow(val qty: Int) : ProductDetailEvent()
    data class AddToWishlist(val productId: String) : ProductDetailEvent()
}

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: RemoteProductEntity? = null,
    val subCategoryProducts: List<RemoteProductEntity>? = null,
    val similarBrandItems: List<RemoteProductEntity>? = null,
    val isItemInCart: Boolean = false,
    val isWishListed: Boolean = false,
    val success: Boolean? = null,
    val error: String? = null
)