package me.mrsajal.flashcart.android.presentation.users.customer.wishlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun Wishlist(navController: NavController) {
    val wishlistViewModel: WishlistViewModel = koinViewModel()
    val wishListUiState by wishlistViewModel.uiState.collectAsState()
    WishlistScreen(
        uiState = wishListUiState,
        fetchData = wishlistViewModel::getWishListItems,
        event = wishlistViewModel::onUiAction,
        navController = navController
    )
}