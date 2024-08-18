package me.mrsajal.flashcart.android.presentation.users.customer.wishlist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.common.util.routes.Routes
import me.mrsajal.flashcart.android.presentation.components.ProductHomeIcon


@Composable
fun WishlistScreen(
    event: (WishlistEvent) -> Unit,
    modifier: Modifier = Modifier,
    uiState: WishListUiState,
    fetchData: () -> Unit,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        fetchData()
    }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = modifier
                        .height(64.dp),
                    contentColor = Color.White,
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                            .statusBarsPadding(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                            )
                        }
                        Text(
                            "Wishlist&Collections", color = Color.White,
                            fontSize = 20.sp
                        )
                        IconButton(onClick = { navController.navigate(Routes.Cart.route) }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint = Color.White
                            )
                        }
                    }
                }
            },

            ) { innerPadding ->
            Box(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    modifier = modifier.padding(4.dp, top = 8.dp),
                    columns = GridCells.Fixed(2),

                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.wishList) { product ->
                        ProductHomeIcon(
                            modifier = modifier
                                .padding(2.dp)
                                .clickable {
                                    navController.navigate(
                                        Routes.ProductDetailScreen.route + "?productId=${product.productId}"
                                    )
                                },
                            itemName = product.productName,
                            itemPrice = product.price,
                            itemImage = product.images?.getOrNull(0) ?: "",
                            onClick = {},
                            isWishListItem = true,
                            onHeartItemClick = { event(WishlistEvent.RemoveItem(product.productId)) }
                        )
                    }
                }
            }
        }
    }
}