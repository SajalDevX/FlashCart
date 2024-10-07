package me.mrsajal.flashcart.android.presentation.users.customer.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun OrdersScreen(
    uiState: OrdersUiState
) {

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                Text(
                    text = uiState.error ?: "Unknown Error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.ordersWithProductDetails.isNullOrEmpty() -> {
                Text(
                    text = "No orders available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.ordersWithProductDetails ?: emptyList()) { order ->
                        OrderCard(order = order)
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(order: OrderWithProductDetails) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* Handle card click, if needed */ },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Order ID: ${order.orderId}", style = MaterialTheme.typography.subtitle1)
            Text(text = "Status: ${order.orderStatus}", style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(8.dp))

            order.items.forEach { item ->
                Text(
                    text = item.product.productName, // Assuming RemoteProductEntity has a 'name' field
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
