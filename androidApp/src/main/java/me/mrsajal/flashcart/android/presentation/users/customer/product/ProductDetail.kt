package me.mrsajal.flashcart.android.presentation.users.customer.product

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.common.util.routes.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetail(
    navController: NavController
) {
    val productDetailViewModel: ProductDetailViewModel = koinViewModel()
    val productDetailUiState = productDetailViewModel.uiState.collectAsStateWithLifecycle().value

    ProductDetailScreen(
        onEvent = productDetailViewModel::onUiEvent,
        uiState = productDetailUiState,
        onNavigateToCartScreen = { navController.navigate(Routes.Cart.route) },
        onBackButtonClick =  { navController.navigateUp() },
        onNavigateToSearchScreen = {},
        navController =navController,
    )

}