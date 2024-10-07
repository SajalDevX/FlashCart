package me.mrsajal.flashcart.android.presentation.users.customer.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.gson.Gson
import me.mrsajal.flashcart.android.navgraph.routes.Routes
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Cart(navController: NavController) {
    val cartViewModel: CartViewModel = koinViewModel()
    val cartUiState = cartViewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    CartScreen(
        cartUiState = cartUiState,
        cartUiAction = cartViewModel::handleAction,
        navigateToProduct = {
                val checkoutInfoJson = Gson().toJson(cartUiState.checkoutInfo)
                navController.navigate(Routes.CheckoutScreen.route + "?checkoutInfo=$checkoutInfoJson")
        }
    )
}

