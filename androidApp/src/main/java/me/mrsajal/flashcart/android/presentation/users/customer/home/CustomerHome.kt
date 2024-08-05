package me.mrsajal.flashcart.android.presentation.users.customer.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomerHome(
    navController: NavController
) {
    val homeViewModel: HomeScreenViewModel = koinViewModel()
    val homeUiState = homeViewModel.homeUiState
    HomeScreen(
        homeUiState = homeUiState,
        homeRefreshState = homeViewModel.homeRefreshState,
        navController = navController,
        onUiAction = homeViewModel::onUiAction
    )
}