package me.mrsajal.flashcart.android.presentation.users.customer.orders.order_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.features.order.data.RemoteOrderEntity
import me.mrsajal.flashcart.features.order.data.OrderItems

@Composable
fun OrderDetailScreen(order: RemoteOrderEntity, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    OrderSummary(order = order)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Order Items", style = MaterialTheme.typography.subtitle1)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(order.orderItems.size) { index ->
                    OrderItemCard(orderItem = order.orderItems[index])
                }
            }
        }
    )
}

@Composable
fun OrderSummary(order: RemoteOrderEntity) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Order ID: ${order.orderId}", style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Order Status: ${order.status}", style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Subtotal: $${order.subTotal}", style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Shipping Charge: $${order.shippingCharge}", style = MaterialTheme.typography.subtitle2)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Total: $${order.subTotal + order.shippingCharge}", style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun OrderItemCard(orderItem: OrderItems) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Product ID: ${orderItem.productId}", style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Quantity: ${orderItem.quantity}", style = MaterialTheme.typography.subtitle2)
        }
    }
}
