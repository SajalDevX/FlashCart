package me.mrsajal.flashcart.android.presentation.users.customer.product.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.mrsajal.flashcart.android.common.theming.Blue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductImagePager(
    modifier: Modifier = Modifier,
    images: List<String>
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })

    Box(
        modifier = modifier
            .aspectRatio(1.2f)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        PageIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            pageSize = images.size,
            selectedPage = pagerState.currentPage
        )
    }
}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = Blue
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

@Preview(showBackground = true)
@Composable
fun ProductImagePagerPreview() {
    val sampleImages = listOf(
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg",
        "https://example.com/image3.jpg"
    )

    ProductImagePager(
        images = sampleImages
    )
}