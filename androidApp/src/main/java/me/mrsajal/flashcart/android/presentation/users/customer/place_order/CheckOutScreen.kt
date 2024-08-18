package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mrsajal.flashcart.android.presentation.users.customer.cart.components.CartListItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    uiState: CheckOutUiState,
    onContinueClick: () -> Unit,
    onChangeAddress: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                BottomAppBar(
                    contentColor = Color.Black,
                    backgroundColor = Color.White,
                    elevation = 0.dp,
                    modifier = modifier.padding(6.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Column {
                            Text(
                                text = "Total",
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "$${uiState.finalAmount}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                        Button(
                            onClick = {
                                onContinueClick()
                            },
                            colors = ButtonDefaults.buttonColors(Color(0xffe6b225)),
                            elevation = ButtonDefaults.elevation(0.dp),
                            modifier = modifier
                                .height(36.dp)
                                .width(144.dp)
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
            Divider()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .height(80.dp)
                            .padding(horizontal = 8.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
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
                                            uiState.selectedAddress?.let { append(it.fatherName) }!!
                                        }
                                        append(", ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            uiState.selectedAddress?.let { append(it.pin) }
                                        }
                                    }, fontSize = 16.sp, color = Color.Black
                                )
                                Text(
                                    text = "${uiState.selectedAddress?.road}, ${uiState.selectedAddress?.city}",
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }
                            TextButton(
                                onClick = {
                                    onChangeAddress()
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
                    }
                }

                items(items = uiState.items) { item ->
                    CartListItem(
                        item = item,
                        quantity = item.qty,
                        isCheckOut = true,
                    )
                }
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(256.dp)
                            .background(Color.White),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Price Details",
                            modifier = modifier.padding(8.dp, top = 16.dp),
                            style = MaterialTheme.typography.h6
                        )
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Total MRP")
                            Text(text = "$${uiState.totalPrice}")
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Discount Price")
                            Text(text = "$${uiState.discount}", color = Color.Green)
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Platform fee")
                            Text(text = "$0.0")
                        }
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Delivery charge")
                            Text(text = "$${uiState.deliveryCharge}")
                        }
                        Divider()
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total Amount",
                                fontWeight = FontWeight(800)
                            )
                            Text(text = "$${uiState.finalAmount}", fontWeight = FontWeight(800))

                        }
                        Divider()
                        Text(
                            text = "You will save $${uiState.totalPrice - uiState.discount} on this order",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(800),
                            color = Color.Green,
                            textAlign = TextAlign.Center,
                            modifier = modifier.padding(start = 16.dp)
                        )
                    }
                }
                item {
                    Text(
                        "Safe and secure payments. Easy returns 100% authentic products.",
                        fontWeight = FontWeight(800),
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}