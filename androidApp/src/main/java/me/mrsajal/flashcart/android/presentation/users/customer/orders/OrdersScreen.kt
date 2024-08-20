//package me.mrsajal.flashcart.android.presentation.users.customer.orders
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Card
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.Scaffold
//import androidx.compose.material.Text
//import androidx.compose.material.TopAppBar
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import me.mrsajal.flashcart.android.common.util.routes.Routes
//
//@Composable
//fun OrdersScreen(
//    modifier: Modifier = Modifier,
//    uiState: OrdersUiState,
////    onEvent: (OrdersEvent) -> Unit,
//    navController: NavController
//) {
//    Scaffold(
//        topBar = {
//
//            TopAppBar(
//                modifier = modifier
//                    .height(64.dp),
//                contentColor = Color.White,
//                elevation = 0.dp
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 8.dp)
//                        .statusBarsPadding(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(
//                            Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Back",
//                            tint = Color.White,
//                        )
//                    }
//                    Text(
//                        "Wishlist&Collections", color = Color.White,
//                        fontSize = 20.sp
//                    )
//                    IconButton(onClick = { navController.navigate(Routes.Cart.route) }) {
//                        Icon(
//                            Icons.Default.ShoppingCart,
//                            contentDescription = "Cart",
//                            tint = Color.White
//                        )
//                    }
//                }
//            }
//        }
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .statusBarsPadding(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//           items(items = uiState.orders?: emptyList()){order->
//               Card {
//                   Row {
//                       AsyncImage(
//                           model = order.orderItem,
//                           contentDescription = order.product.name,
//                           modifier = Modifier.height(100.dp)
//                       )
//                   }
//               }
//           }
//        }
//    }
//}