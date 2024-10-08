package me.mrsajal.flashcart.android.presentation.users.customer.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.navgraph.routes.Routes
import me.mrsajal.flashcart.android.presentation.components.ProductHomeIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onUiAction: (HomeUiAction) -> Unit,
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    homeRefreshState: HomeRefreshState,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeRefreshState.isRefreshing,
        onRefresh = { onUiAction(HomeUiAction.RefreshAction) },
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .padding(14.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(homeUiState.products, key = {product->product.productId}) { product ->
                ProductHomeIcon(
                    modifier = modifier
                        .padding(2.dp)
                        .clickable { navController.navigate(Routes.ProductDetailScreen.route + "?productId=${product.productId}") },
                    itemName = product.productName,
                    itemPrice = product.price,
                    itemImage = product.images?.getOrNull(0) ?: "",
                    onClick = {},
                    onHeartItemClick = {}
                )
            }
        }
        PullRefreshIndicator(
            refreshing = homeRefreshState.isRefreshing,
            state = pullRefreshState,
            modifier = modifier.align(Alignment.TopCenter)
        )
    }
}
