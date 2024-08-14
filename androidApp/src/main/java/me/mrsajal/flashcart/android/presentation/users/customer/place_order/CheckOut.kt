package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckOut(navController: NavController) {
    val viewModel: CheckOutViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState().value

    CheckoutScreen(
        uiState = uiState,
        onEvent = viewModel::handleAction
    )
}