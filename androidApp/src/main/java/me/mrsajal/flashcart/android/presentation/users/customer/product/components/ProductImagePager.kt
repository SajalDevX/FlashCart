package me.mrsajal.flashcart.android.presentation.users.customer.product.components

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.features.products.domain.model.RemoteProductEntity
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImagePager(
    modifier: Modifier = Modifier,
    images: List<String>,
    isWishlisted: Boolean = false,
    product: RemoteProductEntity? = null,
    onWishlistClick: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
            .height(600.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                if (page != 1) {
                    AsyncImage(
                        model = images[page],
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    ProductHighlights(product = product!!, image = images[page])
                }
            }
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row {
                    IconButton(onClick = onWishlistClick) {
                        Icon(
                            painter = painterResource(
                                if (isWishlisted) R.drawable.like_icon_filled
                                else R.drawable.like_icon_outlined
                            ),
                            contentDescription = "Wishlist",
                            tint = if (isWishlisted) Color.Red else Color.Black.copy(0.8f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        shareProduct(context, product)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PageIndicator(
            modifier = Modifier.padding(vertical = 8.dp),
            pageSize = images.size,
            selectedPage = pagerState.currentPage
        )
    }
}

private fun shareProduct(context: Context, product: RemoteProductEntity?) {
    val shareText = product?.let {
        "Check out this product: ${it.productName}\n" +
                "Price: $${it.price}\n" +
                "Discount Price: $${it.discountPrice}\n" +
                "Images: ${it.images?.joinToString(", ")}"
    } ?: "Check out this product"

    ShareCompat.IntentBuilder.from(context as Activity)
        .setType("text/plain")
        .setChooserTitle("Share Product")
        .setText(shareText)
        .startChooser()
}


@Composable
fun ProductHighlights(
    modifier: Modifier = Modifier,
    product: RemoteProductEntity,
    image: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color.Black.copy(0.8f), Color.White.copy(0.5f))
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Key Highlights",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            KeyHighlight("Brand", "")
            KeyHighlight("Size", "")
            KeyHighlight("Interface", "")
        }
    }
}

@Composable
fun KeyHighlight(key: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = key,
            style = MaterialTheme.typography.caption,
            color = Color.LightGray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
    }
}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = Color.DarkGray.copy(0.5f)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageSize) { page ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (page == selectedPage) selectedColor
                        else unselectedColor
                    )
            )
        }
    }
}
