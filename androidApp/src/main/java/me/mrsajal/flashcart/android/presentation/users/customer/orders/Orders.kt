package me.mrsajal.flashcart.android.presentation.users.customer.orders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun Orders(navController: NavController) {
    val viewModel :OrdersViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState()
    OrdersScreen(uiState.value)

}