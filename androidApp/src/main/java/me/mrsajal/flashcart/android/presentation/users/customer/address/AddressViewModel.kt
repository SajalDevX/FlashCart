package me.mrsajal.flashcart.android.presentation.users.customer.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.UserAddress
import me.mrsajal.flashcart.features.profile.domain.usecases.GetProfileUseCase
import me.mrsajal.flashcart.features.profile.domain.usecases.DeleteAddressUseCase


class AddressViewModel(
    private val getUserAddress: GetProfileUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddressUiState())
    val uiState: StateFlow<AddressUiState> = _uiState.asStateFlow()
    init {
        getAddresses()
    }
    private fun getAddresses() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)
            when (val result = getUserAddress()) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false, errorMessage = result.message
                        )
                    }
                }

                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            addresses = result.data?.userDetails?.addresses ?: emptyList(),
                            success = true
                        )
                    }
                }
            }
        }
    }

    private fun deleteAddress(index: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val result = deleteAddressUseCase(index)) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false, errorMessage = result.message
                        )
                    }
                }

                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            success = true
                        )
                    }
                }
            }
        }
    }

    fun onUiEvent(action: AddressEvent) {
        when (action) {
            is AddressEvent.DeleteAddress -> deleteAddress(action.index)
        }
    }
}

sealed class AddressEvent {
    data class DeleteAddress(val index: Int) : AddressEvent()
}

data class AddressUiState(
    val addresses: List<UserAddress>? = emptyList(),
    val success: Boolean = false,
    val errorMessage: String? = "",
    val isLoading: Boolean = false
)