package me.mrsajal.flashcart.android.presentation.users.customer.item_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.products.domain.usecase.SearchProductsUseCase

class SearchViewModel(
    private val searchUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState
    private var searchJob: Job? = null
    fun search() {
        val query = _uiState.value.query

        _uiState.value = _uiState.value.copy(
            isLoading = true,
        )

        viewModelScope.launch {
            try {
                when (val results = searchUseCase(query)) {
                    is Result.Error -> {
                        _uiState.value = _uiState.value.copy(
                            error = results.message,
                            isLoading = false
                        )
                    }

                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            searchResults = results.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    private fun onQueryChange(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            search()
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> search()
            is SearchEvent.OnQueryChange -> onQueryChange(event.query)
        }
    }
}

sealed class SearchEvent {
    data object Search : SearchEvent()
    data class OnQueryChange(val query: String) : SearchEvent()
}

data class SearchUiState(
    val query: String = "",
    val error: String? = null,
    val isLoading: Boolean = false,
    val searchResults: List<RemoteProductEntity> = emptyList()
)



