package me.mrsajal.flashcart.android.presentation.users.customer.wishlist

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.android.presentation.components.ProductHomeIcon
@Composable
fun WishlistScreen(
    event: (WishlistEvent) -> Unit,
    modifier: Modifier = Modifier,
    uiState: WishListUiState,
    fetchData: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorMessage ->
            Toast.makeText(
                context,
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        fetchData()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(14.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(uiState.wishList) { product ->
                ProductHomeIcon(
                    modifier = modifier.padding(2.dp),
                    itemName = product.productName,
                    itemPrice = product.price,
                    itemImage = product.images?.getOrNull(0) ?: "",
                    onClick = {},
                    isWishListItem = true,
                    onHeartItemClick = {event(WishlistEvent.RemoveItem(product.productId))}
                )
            }
        }
    }
}
