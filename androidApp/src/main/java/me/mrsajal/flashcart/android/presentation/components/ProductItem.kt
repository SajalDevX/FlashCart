package me.mrsajal.flashcart.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.common.theming.AppTheme

@Composable
fun ProductHomeIcon(
    modifier: Modifier = Modifier,
    itemName: String,
    itemPrice: Int,
    itemImage: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(240.dp)
            .width(200.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFFFAFAFC),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = modifier.weight(1f),
                model = itemImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier
                    .background(color = Color(0xFFFAFAFC))
                    .fillMaxSize()
                    .weight(1f)
                    .padding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = itemName,
                    fontSize = 12.sp,
                    color = Color(0xFF393F42),
                    modifier = modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight(300)
                )
                Text(
                    text = "$ ${itemPrice.toDouble()}",
                    fontSize = 14.sp,
                    color = Color(0xFF393F42),
                    modifier = modifier
                        .padding(horizontal = 12.dp, 4.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight(800)
                )
                Spacer(modifier = modifier.height(12.dp))
                Button(
                    onClick = { onClick() },
                    modifier = modifier
                        .width(180.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF67C4A7))
                ) {
                    Text(text = stringResource(id = R.string.add_to_cart), color = Color.White)
                }
            }
        }
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductHomeIconPreview() {
    AppTheme {
        ProductHomeIcon(itemName = "Gaming Mouse", itemPrice = 32, itemImage = "") {
        }
    }
}