package me.mrsajal.flashcart.android.presentation.users.customer.cart.components

import ProductRating
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.common.util.formatWithCommas
import me.mrsajal.flashcart.android.presentation.components.VerticalDivider
import me.mrsajal.flashcart.features.cart.domain.model.CartListData
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CartListItem(
    modifier: Modifier = Modifier,
    item: CartListData,
    quantity: Int,
    isSelected: Boolean = false,
    onIncreaseQty: (() -> Unit)?=null,
    onDecreaseQty: (() -> Unit)?=null,
    onCheckedChange: ((Boolean) -> Unit)?=null,
    onRemoveClick: (() -> Unit)?=null,
    onWishlistClick: (() -> Unit)?=null,
    isCheckOut: Boolean = false
) {
    val currentDate = LocalDate.now()
    val deliveryDate = currentDate.plusDays(2)
    val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
    val formattedDate = deliveryDate.format(formatter)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .height(240.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = modifier.weight(5f),
        ) {
            ProductImage(
                imageUrl = item.product.images?.get(0) ?: "",
                isChecked = isSelected,
                onCheckedChange = onCheckedChange,
                onIncreaseQty = onIncreaseQty,
                onDecreaseQty = onDecreaseQty,
                quantity = quantity,
                isCheckOut = isCheckOut
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.product.productName,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.product.productDetail,
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight(400),
                    color = Color.Gray.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ProductRating(
                    rating = 2
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    ) {
                        val discountPercentage =
                            ((item.product.price - item.product.discountPrice!!) / item.product.price * 100).toInt()
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Discount Dropdown",
                            tint = Color.Green,
                            modifier = Modifier
                                .rotate(-90f)
                                .size(12.dp)
                        )
                        Text(
                            text = "${discountPercentage}%",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )
                    }
                    Text(
                        text = "$" + item.product.price.toInt().formatWithCommas(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        color = Color.Gray.copy(alpha = 0.7f),
                        textDecoration = TextDecoration.LineThrough
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$" + (item.product.discountPrice?.toInt()?.formatWithCommas()
                            ?: ""),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(600)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.product.buyOneGetOne!!,
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight(400),
                    color = Color.Blue.copy(alpha = 0.7f)
                )
            }
        }
        Text(
            text = "Delivery expected by $formattedDate",
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.caption,
            color = Color.Green
        )
        Row(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onRemoveClick != null) {
                IconButton(
                    onClick = onRemoveClick,
                    modifier = modifier
                        .border(1.dp, Color.Gray.copy(alpha = 0.2f))
                        .weight(1f)
                ) {
                    Row {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remove",
                            modifier = Modifier.size(24.dp),
                            tint = Color.DarkGray.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Remove",
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray
                        )
                    }
                }
            }
            if (onWishlistClick != null) {
                IconButton(
                    onClick = onWishlistClick,
                    modifier = modifier
                        .border(1.dp, Color.Gray.copy(alpha = 0.2f))
                        .weight(1f)
                ) {
                    Row {
                        Icon(
                            Icons.Default.AddCircle,
                            contentDescription = "Add to Wishlist",
                            modifier = Modifier.size(24.dp),
                            tint = Color.DarkGray.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Buy later",
                            style = MaterialTheme.typography.body1,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductImage(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    imageUrl: String,
    onCheckedChange: ((Boolean) -> Unit)?=null,
    onDecreaseQty: (() -> Unit)?=null,
    onIncreaseQty: (() -> Unit)?=null,
    quantity: Int,
    isCheckOut: Boolean = false
) {
    Box(
        modifier = modifier
            .width(100.dp)
            .height(150.dp)
            .background(Color(0xFFFAFAFC)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
                if(!isCheckOut){
                    Checkbox(
                        checked = isChecked,
                        onCheckedChange = onCheckedChange,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Gray.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp)
                    .weight(0.2f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onDecreaseQty != null) {
                    IconButton(
                        onClick = onDecreaseQty,
                        modifier = Modifier.size(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.minus),
                            contentDescription = "Decrease",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                VerticalDivider()
                Text(text = quantity.toString(), style = MaterialTheme.typography.body1)
                VerticalDivider()
                if (onIncreaseQty != null) {
                    IconButton(
                        onClick = onIncreaseQty,
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProductImagePreview() {
    ProductImage(
        imageUrl = "https://picsum.photos/200/300",
        isChecked = true,
        onCheckedChange = {},
        onIncreaseQty = {},
        onDecreaseQty = {},
        quantity = 2
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun CartListItemPreview() {
    CartListItem(
        item = CartListData(
            product = RemoteProductEntity(
                productId = "1",
                productName = "Fair and Handsome Creme",
                price = 5000.0,
                productDetail = "Description",
                categoryId = "Category",
                images = listOf("https://picsum.photos/200/300"),
                ratingId = listOf(""),
                discountPrice = 4000.0,
                productQuantity = 3,
                brandId = "",
                buyOneGetOne = "Buy One Get One Free",
                hotDeal = "",
                productCode = "",
                subCategoryId = "",
                userId = "",
                videoLink = ""
            ),
            qty = 2
        ),
        quantity = 2,
        isSelected = true,
        onIncreaseQty = {},
        onDecreaseQty = {},
        onCheckedChange = {},
        onRemoveClick = {},
        onWishlistClick = {}
    )
}
