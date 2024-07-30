package me.mrsajal.flashcart.android.presentation.users.customer.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.products.domain.usecase.GetAllProductsUseCase
import me.mrsajal.flashcart.common.utils.Result

class HomeScreenViewModel(
    private val getAllProductsUseCase: GetAllProductsUseCase,
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set
    var homeRefreshState by mutableStateOf(HomeRefreshState())
        private set


    fun fetchData() {
        homeRefreshState = homeRefreshState.copy(isRefreshing = true)
        viewModelScope.launch {

            try {
                delay(1000)
                val result = getAllProductsUseCase(
                    limit = 10,
                    offset = 0,
                    maxPrice = null,
                    minPrice = null
                )
                Log.d("HomeScreenViewModel", "Result: ${result.data}")

                when (result) {
                    is Result.Success -> {
                        val productData = result.data
                        homeUiState = if (productData != null) {
                            homeUiState.copy(
                                isLoading = false,
                                products = productData,
                                errorMessage = null
                            )
                        } else {
                            homeUiState.copy(
                                isLoading = false,
                                errorMessage = "No products available."
                            )
                        }
                    }

                    is Result.Error -> {
                        Log.e("HomeScreenViewModel", "Error: ${result.data}")
                        homeUiState = homeUiState.copy(
                            isLoading = false,
                            errorMessage = "Failed to load products: ${result.message}"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeScreenViewModel", "Exception: ${e.message}", e)
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
            HomeUiAction.LoadMoreProductsAction -> {}
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
