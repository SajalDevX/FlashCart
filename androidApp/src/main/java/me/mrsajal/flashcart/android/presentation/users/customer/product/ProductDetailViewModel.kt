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
import me.mrsajal.flashcart.features.profile.data.UserAddress
import me.mrsajal.flashcart.features.profile.domain.usecases.GetProfileUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.AddItemsToWishlistUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.GetWishListItemsUseCase
import me.mrsajal.flashcart.features.wishlist.domain.usecases.RemoveItemsFromWishlistUseCase

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val fetchProductDataUseCase: GetProductDetailsUseCase,
    private val getFeedProducts: GetProductsBySubCategory,
    private val getProductsByBrand: GetProductsByBrand,
    private val addItemsToCart: AddItemToCartUseCase,
    private val addItemsToWishlistUseCase: AddItemsToWishlistUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val removeItemsFromWishlistUseCase: RemoveItemsFromWishlistUseCase,
    private val getWishlistItemsUseCase: GetWishListItemsUseCase,
    private val userProfileUseCase: GetProfileUseCase,
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
                isWishlistItem()
                getAddressData()
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

    private fun toggleWishlist() {
        viewModelScope.launch {
            currentId?.let {
                if (_uiState.value.isWishListed) {
                    removeFromWishlist(it)
                } else {
                    addToWishList()
                }
            }
        }
    }

    private fun removeFromWishlist(productId: String) {
        viewModelScope.launch {
            when (val result = removeItemsFromWishlistUseCase(productId = productId)) {
                is Result.Error -> {
                    _uiState.update { it.copy(error = result.message) }
                }

                is Result.Success -> {
                    _uiState.update { it.copy(success = result.data, isWishListed = false) }
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

    private fun isWishlistItem() {
        viewModelScope.launch {
            delay(100)
            when (val result = getWishlistItemsUseCase()) {
                is Result.Error -> {
                    _uiState.update { it.copy(error = result.message) }
                }

                is Result.Success -> {
                    _uiState.update { product -> product.copy(isWishListed = result.data!!.any { it.productId == currentId }) }
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
        }
    }

    private fun selectAddress(address: UserAddress) {
        _uiState.update { it.copy(selectedAddress = address) }
    }

    fun onUiEvent(action: ProductDetailEvent) {
        when (action) {
            is ProductDetailEvent.AddToCart -> addToCart(action.qty)
            is ProductDetailEvent.ToggleWishlist -> toggleWishlist()
            is ProductDetailEvent.BuyNow -> addToWishList()
            ProductDetailEvent.FetchData -> {
                currentId?.let {
                    fetchProductData(it)
                    getSubCategoryProducts()
                    getSimilarBrandItems()
                }
            }

            is ProductDetailEvent.SelectAddress -> selectAddress(action.address)
        }
    }
}

sealed class ProductDetailEvent {
    data object FetchData : ProductDetailEvent()
    data class AddToCart(val qty: Int) : ProductDetailEvent()
    data class BuyNow(val qty: Int) : ProductDetailEvent()
    data class ToggleWishlist(val productId: String) : ProductDetailEvent()
    data class SelectAddress(val address: UserAddress) : ProductDetailEvent()
}

data class ProductDetailUiState(
    val address: List<UserAddress>? = emptyList(),
    val selectedAddress: UserAddress? = null,
    val isLoading: Boolean = false,
    val product: RemoteProductEntity? = null,
    val subCategoryProducts: List<RemoteProductEntity>? = null,
    val similarBrandItems: List<RemoteProductEntity>? = null,
    val isItemInCart: Boolean = false,
    val isWishListed: Boolean = false,
    val success: Boolean? = null,
    val error: String? = null,
)
