package me.mrsajal.flashcart.android.presentation.users.customer.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cart(navController: NavController) {
    val cartViewModel: CartViewModel = koinViewModel()
    val cartUiState = cartViewModel.uiState.collectAsStateWithLifecycle().value

    CartScreen(
        cartUiState = cartUiState,
        cartUiAction = cartViewModel::handleAction,
        navigateToProduct = {
        }
    )
}
