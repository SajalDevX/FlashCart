package me.mrsajal.flashcart.android.presentation.users.customer.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.common.util.routes.Routes
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileDataScreen(navController: NavController) {
    val viewModel: ProfileViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    ProfileScreen(
        uiState = uiState,
        onLogout = {},
        onNaviToEditProfile = { navController.navigate(Routes.EditProfile.route) },
        onNavToAbout = {},
        onNavToOrders = {navController.navigate(Routes.OrderListScreen.route)},
        onNavToCart = {navController.navigate(Routes.Cart.route){
            popUpTo(Routes.Cart.route) {
                inclusive = true
            }
        } },
        onNavToHelp = {},
        onNavToReviews = {},
        onNaviToAddress = { navController.navigate(Routes.AddressMainScreen.route){
            popUpTo(Routes.AddressMainScreen.route) {
                inclusive = true
            }
        } },
        onNavToSettings = {},
        onNavToWishlist = {navController.navigate(Routes.Wishlist.route){
            popUpTo(Routes.Wishlist.route) {
                inclusive = true
            }
        } }
    )
}