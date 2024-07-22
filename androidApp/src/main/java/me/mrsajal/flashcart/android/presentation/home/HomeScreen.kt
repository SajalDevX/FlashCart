package me.mrsajal.flashcart.android.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.products.domain.model.RemoteProductEntity
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
homeUiState: HomeUiState) {
    val context = LocalContext.current

    LaunchedEffect(homeUiState.errorMessage) {
        homeUiState.errorMessage?.let { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    LazyColumn {
        items(homeUiState.products) { product ->
            Text(text = product.productName)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

