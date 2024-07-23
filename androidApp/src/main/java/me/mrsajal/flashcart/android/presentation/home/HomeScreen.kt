package me.mrsajal.flashcart.android.presentation.home

import android.widget.Toast
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
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState
) {
    val context = LocalContext.current

    LaunchedEffect(homeUiState.errorMessage) {
        homeUiState.errorMessage?.let { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    LazyVerticalGrid(modifier = Modifier.padding(14.dp),columns = GridCells.Fixed(2)) {
        items(homeUiState.products) { product ->
            ProductHomeIcon(
                modifier = modifier.padding(2.dp),
                itemName = product.productName,
                itemPrice = product.price,
                itemImage = product.images?.getOrNull(0) ?: "",
                onClick = {}
            )
        }
    }
}
