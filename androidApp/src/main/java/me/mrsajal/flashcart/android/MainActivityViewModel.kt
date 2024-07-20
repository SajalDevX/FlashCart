package me.mrsajal.flashcart.android

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.mrsajal.flashcart.common.data.local.UserSettings

class MainActivityViewModel(
    dataStore: DataStore<UserSettings>
) : ViewModel() {
    val uiState :StateFlow<MainActivityUiState> = dataStore.data.map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000)
    )
}
sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val currentUser: UserSettings) : MainActivityUiState
}