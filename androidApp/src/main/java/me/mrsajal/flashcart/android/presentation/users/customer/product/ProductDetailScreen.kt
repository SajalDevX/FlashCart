package me.mrsajal.flashcart.android.presentation.users.customer.product

import ProductRating
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.mrsajal.flashcart.android.common.util.formatWithCommas
import me.mrsajal.flashcart.android.presentation.components.VerticalDivider
import me.mrsajal.flashcart.android.presentation.users.customer.cart.components.AddressBottomSheetContent
import me.mrsajal.flashcart.android.presentation.users.customer.product.components.ProductImagePager
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import me.mrsajal.flashcart.features.profile.data.UserAddress

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUiState,
    onEvent: (ProductDetailEvent) -> Unit,
    onNavigateToCartScreen: () -> Unit,
    onBackButtonClick: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    navController: NavController,
) {
    val currentUiState = rememberUpdatedState(newValue = uiState)
    val scrollState = rememberLazyListState()
    var isTopBarVisible by rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemScrollOffset }
            .map { offset -> offset > 0 }
            .distinctUntilChanged()
            .collect { visible ->
                isTopBarVisible = !visible
            }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AnimatedVisibility(
                visible = isTopBarVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TopAppBar(
                    modifier = modifier
                        .background(Color.Transparent)
                        .height(64.dp),
                    contentColor = Color.Black,
                    backgroundColor = Color.White,
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
                        IconButton(onClick = { onBackButtonClick() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black,
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { onNavigateToSearchScreen() }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Black
                                )
                            }
                            IconButton(onClick = { onNavigateToCartScreen() }) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Cart",
                                    tint = Color.Black
                                )
                            }
                        }
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
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = { onEvent(ProductDetailEvent.AddToCart(1)) },
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
                        onClick = { onEvent(ProductDetailEvent.BuyNow(1)) },
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
        if (currentUiState.value.isLoading) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            ModalBottomSheetLayout(
                sheetState = bottomSheetState,
                sheetShape = RectangleShape,
                sheetContent = {
                    AddressBottomSheetContent(
                        addresses = currentUiState.value.address ?: emptyList(),
                        selectedAddress = currentUiState.value.selectedAddress,
                        onDismissModalSheet = {
                            coroutineScope.launch { bottomSheetState.hide() }
                        },
                        onSelectAddress = { address ->
                            onEvent(ProductDetailEvent.SelectAddress(address))
                            coroutineScope.launch { bottomSheetState.hide() }
                        },
                    )
                }
            ) {
                LazyColumn(
                    state = scrollState,
                    contentPadding = innerPadding,
                    modifier = modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    item {
                        Text(
                            text = ("Brand  " + currentUiState.value.product?.productName),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        ProductRating(
                            rating = 2,
                            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                            size = 24.dp
                        )
                        Spacer(modifier = modifier.height(16.dp))
                        ProductImagePager(
                            modifier = Modifier
                                .fillMaxWidth(),
                            product = currentUiState.value.product,
                            isWishlisted = uiState.isWishListed,
                            images = currentUiState.value.product?.images ?: listOf(),
                            onWishlistClick = { onEvent(ProductDetailEvent.ToggleWishlist(productId = currentUiState.value.product!!.productId)) }
                        )
                        Spacer(modifier = modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.Green)
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "Super Saver",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    val discountPercentage =
                                        ((uiState.product?.price!!.minus(uiState.product.discountPrice!!)) / uiState.product.price * 100).toInt()
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Discount Dropdown",
                                        tint = Color.Green,
                                        modifier = Modifier
                                            .rotate(-90f)
                                            .size(30.dp)
                                    )
                                    Text(
                                        text = "${discountPercentage}%",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Green
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "$" + uiState.product.price.toInt()
                                            .formatWithCommas(),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight(600),
                                        color = Color.Gray.copy(alpha = 0.7f),
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "$" + (uiState.product.discountPrice?.toInt()
                                            ?.formatWithCommas() ?: ""),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight(600)
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .height(14.dp)
                            )

                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .background(Color(0xffaedae6).copy(alpha = 0.5f))
                                    .fillMaxWidth()
                            )
                            Column(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                if (currentUiState.value.selectedAddress != null) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = buildAnnotatedString {
                                                    append("Deliver to: ")
                                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append(currentUiState.value.selectedAddress!!.fatherName)
                                                    }
                                                    append(", ")
                                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                        append(currentUiState.value.selectedAddress!!.pin)
                                                    }
                                                },
                                                fontSize = 16.sp,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = "${currentUiState.value.selectedAddress!!.road}, ${currentUiState.value.selectedAddress!!.city}, ${currentUiState.value.selectedAddress!!.state}",
                                                fontSize = 14.sp,
                                                color = Color.Gray
                                            )
                                        }

                                        TextButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    bottomSheetState.show()
                                                }
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
                                } else {
                                    TextButton(
                                        onClick = {
                                            coroutineScope.launch { bottomSheetState.show() }
                                        }
                                    ) {
                                        Text(
                                            text = "Select Address",
                                            color = MaterialTheme.colors.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        uiState = ProductDetailUiState(
            isLoading = false,
            product = RemoteProductEntity(
                productId = "1",
                productName = "Example Product",
                price = 1000.0,
                discountPrice = 800.0,
                images = listOf(
                    "https://via.placeholder.com/150",
                    "https://via.placeholder.com/150"
                ),
                ratingId = listOf(),
                productDetail = "This is an example product",
                categoryId = "Example Category",
                brandId = "Example Brand",
                subCategoryId = "100",
                buyOneGetOne = "",
                hotDeal = "",
                productCode = "",
                userId = "",
                videoLink = "",
                productQuantity = 9,
                ),
            isWishListed = false,
            address = listOf(
                UserAddress(
                    fatherName = "John Doe",
                    pin = "123456",
                    road = "123 Main St",
                    city = "Springfield",
                    state = "IL",
                    mobileNumber = "",
                    motherName = "Kakali jana"
                )
            ),
            selectedAddress = UserAddress(
                fatherName = "John Doe",
                pin = "123456",
                road = "123 Main St",
                city = "Springfield",
                state = "IL",
                mobileNumber = "",
                motherName = "Kakali jana"
            )
        ),
        onEvent = {},
        onNavigateToCartScreen = {},
        onBackButtonClick = {},
        onNavigateToSearchScreen = {},
        navController = NavController(LocalContext.current),
    )
}