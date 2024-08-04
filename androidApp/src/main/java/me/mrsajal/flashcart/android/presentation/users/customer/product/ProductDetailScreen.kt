package me.mrsajal.flashcart.android.presentation.users.customer.product

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mrsajal.flashcart.android.presentation.components.VerticalDivider

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    productDetailUiState: ProductDetailUiState,
    productDetailEvent: (ProductDetailEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = modifier
                    .background(Color.Transparent)
                    .height(64.dp)
                    .border(1.dp, Color.Gray.copy(0.3f)),
                contentColor = Color.Black,
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .statusBarsPadding(),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                        )
                    }
                    Text(
                        text = "Product Detail",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color.Black
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .height(64.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray.copy(0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Button(
                        onClick = { /* Handle Add to Cart action */ },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Text(text = "Add to Cart", fontSize = 16.sp)
                    }
                    VerticalDivider()
                    Button(
                        onClick = { /* Handle Buy Now action */ },
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(Color(0xffe6b225)),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        Text(text = "Buy Now", fontSize = 16.sp)
                    }
                }

            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {

        }
    }
}

@Preview
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        productDetailUiState = ProductDetailUiState(),
        productDetailEvent = {}
    )
}