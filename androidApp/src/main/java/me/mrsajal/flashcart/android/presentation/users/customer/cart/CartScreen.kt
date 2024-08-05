package me.mrsajal.flashcart.android.presentation.users.customer.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.android.common.util.formatWithCommas
import me.mrsajal.flashcart.android.presentation.users.customer.cart.components.AddressBottomSheetContent
import me.mrsajal.flashcart.android.presentation.users.customer.cart.components.CartListItem

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartUiAction: (CartUiAction) -> Unit,
    cartUiState: CartScreenUiState,
    navigateToProduct: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    if (cartUiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetShape = RectangleShape,
            sheetContent = {
                AddressBottomSheetContent(
                    addresses = cartUiState.address ?: emptyList(),
                    selectedAddress = cartUiState.selectedAddress,
                    onDismissModalSheet = {
                        coroutineScope.launch { bottomSheetState.hide() }
                    },
                    onSelectAddress = { address ->
                        cartUiAction(CartUiAction.SelectAddress(address))
                        coroutineScope.launch { bottomSheetState.hide() }
                    },
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = modifier
                            .background(Color.Transparent)
                            .border(1.dp, Color.Gray.copy(0.3f)),
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

                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .padding(12.dp)
                    ) {
                        if (cartUiState.selectedAddress != null) {
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = buildAnnotatedString {
                                            append("Deliver to: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(cartUiState.selectedAddress.fatherName)
                                            }
                                            append(", ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(cartUiState.selectedAddress.pin)
                                            }
                                        },
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "${cartUiState.selectedAddress.road}, ${cartUiState.selectedAddress.city}",
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            bottomSheetState.show()
                                        }
                                    },
                                    modifier = modifier
                                        .border(1.dp, Color.Gray.copy(0.4f))
                                        .padding(horizontal = 8.dp)
                                        .height(40.dp)
                                ) {
                                    Text(
                                        text = "Change",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.Blue
                                    )
                                }
                            }
                        } else {
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "No address selected",
                                    color = Color.Gray.copy(0.5f),
                                    fontSize = 16.sp,
                                )
                                TextButton(
                                    onClick = { /* TODO: Implement add address logic */ },
                                    modifier = modifier
                                        .border(1.dp, Color.Gray.copy(0.4f))
                                        .padding(horizontal = 8.dp)
                                        .height(40.dp)
                                ) {
                                    Text(
                                        text = "Add",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp,
                                        color = Color.Blue
                                    )
                                }
                            }
                        }
                    }
                    Divider()
                    LazyColumn(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        items(cartUiState.cartItems) { item ->
                            Column {
                                CartListItem(
                                    item = item,
                                    quantity = item.qty,
                                    isSelected = cartUiState.selectedItems.contains(item.product.productId),
                                    onIncreaseQty = {
                                        cartUiAction(
                                            CartUiAction.IncreaseItemCount(
                                                item.product.productId
                                            )
                                        )
                                    },
                                    onDecreaseQty = { cartUiAction(CartUiAction.ReduceItemCount(item.product.productId)) },
                                    onRemoveClick = { cartUiAction(CartUiAction.RemoveItem(item.product.productId)) },
                                    onCheckedChange = { isChecked ->
                                        cartUiAction(
                                            CartUiAction.SelectItem(
                                                item.product.productId,
                                                isChecked
                                            )
                                        )
                                    },
                                    onWishlistClick = { cartUiAction(CartUiAction.AddToWishlist(item.product.productId)) }
                                )
                                Spacer(
                                    modifier = modifier
                                        .height(8.dp)
                                        .background(Color.Gray.copy(0.06f))
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
