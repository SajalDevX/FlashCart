package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    uiState: CheckOutUiState,
    onEvent: (CheckOutUiAction) -> Unit
) {
    Button(
        onClick = { onEvent(CheckOutUiAction.PlaceOrder) }
    ) {
        Text(text = "Place Order")
    }
}