package me.mrsajal.flashcart.android.presentation.users.customer.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mrsajal.flashcart.android.common.util.formatWithCommas
import me.mrsajal.flashcart.android.presentation.users.customer.cart.components.CartListItem


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartUiAction: (CartUiAction) -> Unit,
    cartUiState: CartScreenUiState,
    navigateToProduct: (Int) -> Unit,
    fetchData: () -> Unit
) {
    LaunchedEffect(Unit) {
        fetchData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.background(Color.Transparent),
                contentColor = Color.Black,
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                Text(
                    text = "My Cart",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = modifier.padding(16.dp)
                )
            }
        },
        bottomBar = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(bottom = 60.dp)
            ) {
                BottomAppBar(
                    contentColor = Color.Black,
                    backgroundColor = Color.White,
                    elevation = 0.dp
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        if (cartUiState.selectedItems.isNotEmpty()) {
                            Column(
                                modifier = modifier
                                    .fillMaxHeight()
                                    .width(196.dp)
                            ) {
                                Text(
                                    text = cartUiState.selectedTotalPrice.formatWithCommas(),
                                    fontWeight = FontWeight(200),
                                    textDecoration = TextDecoration.LineThrough,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "${cartUiState.selectedDiscountedPrice.formatWithCommas()}/-",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                            }
                        } else {
                            Text("No items selected")
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(Color(0xffe6b225)),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = modifier
                                .height(36.dp)
                                .width(144.dp)
                        ) {
                            Text("Place Order")
                        }
                    }
                }
            }
            Divider()
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            items(cartUiState.cartItems) { item ->
                CartListItem(
                    item = item,
                    quantity = item.qty,
                    isSelected = cartUiState.selectedItems.contains(item.product.productId),
                    onIncreaseQty = { cartUiAction(CartUiAction.IncreaseItemCount(item.product.productId)) },
                    onDecreaseQty = { cartUiAction(CartUiAction.ReduceItemCount(item.product.productId)) },
                    onRemoveClick = { cartUiAction(CartUiAction.RemoveItem(item.product.productId)) },
                    onCheckedChange = { isChecked ->
                        cartUiAction(CartUiAction.SelectItem(item.product.productId, isChecked))
                    },
                    onWishlistClick = { }
                )
                Spacer(modifier = modifier.height(8.dp))
            }
        }
    }
}
