package me.mrsajal.flashcart.android.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.common.utils.Result
import me.mrsajal.flashcart.features.profile.data.UserEntity
import me.mrsajal.flashcart.features.profile.domain.usecases.GetProfileUseCase

class ProfileViewModel(
    private val fetchUserProfile: GetProfileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        fetchProfile()
    }
    private fun fetchProfile() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(1000)
            when (val result = fetchUserProfile()) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            profile = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}

data class ProfileUiState(
    val profile: UserEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)