package me.mrsajal.flashcart.android.presentation.users.customer.item_search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.presentation.components.ProductHomeIcon
import me.mrsajal.flashcart.android.presentation.users.customer.item_search.components.SearchBar
import me.mrsajal.flashcart.android.common.util.routes.Routes

@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: SearchUiState,
    event: (SearchEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xff799efd), Color(0xff799efd).copy(0.2f)),

                        )
                    )
                    .height(64.dp),
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                contentColor = Color.White
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    SearchBar(
                        text = state.query,
                        readOnly = false,
                        onValueChange = {
                            event(SearchEvent.OnQueryChange(it))
                            event(SearchEvent.Search)
                        },
                        onSearch = {
                            event(SearchEvent.Search)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(8.dp)
                .padding(innerPadding)
                .statusBarsPadding()
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(14.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(items = state.searchResults) { item ->
                    ProductHomeIcon(
                        modifier = modifier
                            .padding(2.dp)
                            .clickable { navController.navigate(Routes.ProductDetailScreen.route + "?productId=${item.productId}") },
                        itemName = item.productName,
                        itemPrice = item.price,
                        itemImage = item.images?.getOrNull(0) ?: "",
                        onClick = {},
                        onHeartItemClick = {}
                    )
                }
            }
        }
    }
}
