package me.mrsajal.flashcart.android.presentation.users.customer.product

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductDetail(
    modifier: Modifier = Modifier
) {
    val viewModel: ProductDetailViewModel = koinViewModel()
    val onEvent = viewModel::onUiEvent
    LaunchedEffect(Unit) {
        onEvent(ProductDetailEvent.FetchData)
    }
    ProductDetailScreen(
        productDetailUiState = viewModel.uiState,
        productDetailEvent = onEvent,
    )

}