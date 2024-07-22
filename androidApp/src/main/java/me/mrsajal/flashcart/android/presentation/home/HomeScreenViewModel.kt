package me.mrsajal.flashcart.android.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.products.domain.usecase.GetAllProductsUseCase
import me.mrsajal.flashcart.common.utils.Result

class HomeScreenViewModel(
    private val getAllProductsUseCase: GetAllProductsUseCase,
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set
    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set

    init {
        fetchData()
    }

    private fun fetchData(
    ) {
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)
        viewModelScope.launch {
            try {
                val result = getAllProductsUseCase(
                    limit = 3,
                    offset = 0,
                    maxPrice = null,
                    minPrice = null
                )
                val productData = result.data
                homeUiState = if (productData != null) {
                    homeUiState.copy(
                        isLoading = false,
                        products = productData
                    )
                } else {
                    homeUiState.copy(
                        isLoading = false,
                        errorMessage = "Failed to load Products"
                    )
                }
            } catch (e: Exception) {
                homeUiState = homeUiState.copy(
                    isLoading = false,
                    errorMessage = "An error occurred: ${e.message}"
                )
            } finally {
                homeRefreshState = homeRefreshState.copy(isRefreshing = false)
            }
        }
    }

    fun onUiAction(uiAction: HomeUiAction) {
        when (uiAction) {
            HomeUiAction.RefreshAction -> fetchData()
            HomeUiAction.LoadMoreProductsAction -> TODO()
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val products: List<RemoteProductEntity> = emptyList(),
    val success: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

data class HomeRefreshState(
    val isRefreshing: Boolean = false,
    val refreshErrorMessage: String? = null
)

sealed interface HomeUiAction {
    data object RefreshAction : HomeUiAction
    data object LoadMoreProductsAction : HomeUiAction
}
