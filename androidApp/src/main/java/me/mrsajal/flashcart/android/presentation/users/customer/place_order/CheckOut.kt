package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.android.presentation.users.customer.address.AddressCard
import me.mrsajal.flashcart.android.presentation.users.customer.place_order.components.LinearProgressBar
import org.koin.androidx.compose.koinViewModel

val progressbarWidth = 480.dp
val steps = listOf("Address", "Checkout", "Payment")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CheckOut(
    navController: NavController
) {
    val viewModel: CheckOutViewModel = koinViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val pagerState = rememberPagerState(initialPage = 1)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Order Summary") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            if (pagerState.currentPage == 0 && !uiState.isLoading) {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        Text("Continue")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Box(
                modifier = Modifier
                    .height(80.dp)
                    .background(Color.White)
                    .width(progressbarWidth)
                    .padding(horizontal = 16.dp)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressBar(
                    steps = steps,
                    currentStep = pagerState.currentPage
                )

            }
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalPager(
                count = 3,
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
            ) { page ->
                when (page) {
                    0 -> {
                        if (uiState.isAddressLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.LightGray.copy(alpha = 0.05f)),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                item {
                                    TextButton(
                                        onClick = {

                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(64.dp)
                                            .background(Color.White),
                                    ) {
                                        Text(
                                            text = "Add a new address",
                                            style = MaterialTheme.typography.h5
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                                val addresses = uiState.address
                                items(addresses) { address ->
                                    AddressCard(
                                        modifier = Modifier
                                            .background(Color.White)
                                            .padding(8.dp),
                                        isCheckout = true,
                                        userAddress = address,
                                        selectedAddress = uiState.selectedAddress,
                                        onSelectAddress = { selectedAddress ->
                                            viewModel.handleAction(
                                                CheckOutUiAction.SelectAddress(
                                                    selectedAddress
                                                )
                                            )
                                        },
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }
                    }

                    1 -> {
                        CheckoutScreen(
                            uiState = uiState,
                            onChangeAddress = {
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            },
                            onContinueClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(2)
                                }
                            }
                        )
                    }

                    2 -> {
                        PaymentScreen(
                            navController = navController,
                            onPaymentSuccess = {
                               viewModel.handleAction(CheckOutUiAction.PlaceOrder)
                            }
                        )
                    }
                }
            }
        }
    }
}

