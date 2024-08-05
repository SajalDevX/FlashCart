package me.mrsajal.flashcart.android.presentation.users.customer.product

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.cart.domain.usecases.AddItemToCartUseCase
import me.mrsajal.flashcart.features.cart.domain.usecases.GetCartItemsUseCase
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductDetailsUseCase
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsByBrand
import me.mrsajal.flashcart.features.products.domain.usecase.GetProductsBySubCategory
import me.mrsajal.flashcart.features.wishlist.domain.usecases.AddItemsToWishlistUseCase

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val fetchProductDataUseCase: GetProductDetailsUseCase,
    private val getFeedProducts: GetProductsBySubCategory,
    private val getProductsByBrand: GetProductsByBrand,
    private val addItemsToCart: AddItemToCartUseCase,
    private val addItemsToWishlistUseCase: AddItemsToWishlistUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    private var currentId: String? = null
    private var brandId: String? = null
    private var subCategoryId: String? = null

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            Log.d("ProductDetailViewModel", "Item Id: $productId")
            if (productId.isNotEmpty()) {
                currentId = productId
                Log.d("ProductDetailViewModel", "Item currentId: $currentId")
                fetchProductData(productId)
            }
        }
    }

    private fun fetchProductData(id: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            when (val result = fetchProductDataUseCase(id)) {
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                is Result.Success -> {
                    brandId = result.data?.brandId
                    subCategoryId = result.data?.subCategoryId
                    _uiState.update { it.copy(isLoading = false, product = result.data) }
                }
            }
        }
    }

    private fun addToCart(qty: Int) {
        viewModelScope.launch {
            currentId?.let {
                when (val result = addItemsToCart(productId = it, qty = qty)) {
                    is Result.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(success = result.data) }
                    }
                }
            }
        }
    }

    private fun addToWishList() {
        viewModelScope.launch {
            currentId?.let {
                when (val result = addItemsToWishlistUseCase(productId = it)) {
                    is Result.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(success = result.data, isWishListed = true) }
                    }
                }
            }
        }
    }

    private fun getSubCategoryProducts() {
        subCategoryId?.let { subCategoryId ->
            viewModelScope.launch {
                when (val result = getFeedProducts(subCategoryId)) {
                    is Result.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(subCategoryProducts = result.data) }
                    }
                }
            }
        }
    }

    private fun getSimilarBrandItems() {
        brandId?.let { brandId ->
            viewModelScope.launch {
                when (val result = getProductsByBrand(brandId)) {
                    is Result.Error -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                    is Result.Success -> {
                        _uiState.update { it.copy(similarBrandItems = result.data) }
                    }
                }
            }
        }
    }

    fun onUiEvent(action: ProductDetailEvent) {
        when (action) {
            is ProductDetailEvent.AddToCart -> addToCart(action.qty)
            is ProductDetailEvent.AddToWishlist -> addToWishList()
            is ProductDetailEvent.BuyNow -> addToWishList()
            ProductDetailEvent.FetchData -> {
                currentId?.let {
                    fetchProductData(it)
                    getSubCategoryProducts()
                    getSimilarBrandItems()
                }
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
