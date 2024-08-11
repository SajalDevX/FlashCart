package me.mrsajal.flashcart.android.presentation.profile

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
        onNaviToEditProfile = {navController.navigate(Routes.EditProfile.route)},
        onNavToAbout = {},
        onNavToOrders = {},
        onNavToCart = {},
        onNavToHelp = {},
        onNavToReviews = {},
        onNaviToAddress = {},
        onNavToSettings = {},
        onNavToWishlist = {}
    )
}