package me.mrsajal.flashcart.android.presentation.users.customer.item_search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun Search(navController: NavController) {
    val viewModel:SearchViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    SearchScreen(
        state = uiState,
        event = viewModel::onEvent,
        modifier = Modifier,
        navController = navController
    )
}