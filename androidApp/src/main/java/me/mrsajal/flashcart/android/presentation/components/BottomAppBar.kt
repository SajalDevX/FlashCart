package me.mrsajal.flashcart.android.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ButtonDefaults.IconSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.common.theming.AppTheme
import me.mrsajal.flashcart.android.common.theming.SmallSpacing

@Composable
fun BottomAppBar(
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp), // Adjusted height for better vertical centering
        backgroundColor = MaterialTheme.colors.background,
        elevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = index == selectedItem,
                onClick = { onItemClick(index) }, // Fixed onClick action
                selectedContentColor = Color(0xFF67C4A7),
                unselectedContentColor = Color(0xFF4E4B66),
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center, // Center items vertically
                        modifier = Modifier.height(56.dp) // Adjust height for vertical centering
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize),
                        )
                        Spacer(modifier = Modifier.height(SmallSpacing))
                        Text(text = item.text, style = MaterialTheme.typography.overline)
                    }
                }
            )
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    AppTheme {
        BottomAppBar(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.homeicon, text = "Home"),
                BottomNavigationItem(icon = R.drawable.hearticon, text = "Wishlist"),
                BottomNavigationItem(icon = R.drawable.papericon, text = "History"),
                BottomNavigationItem(icon = R.drawable.profileicon, text = "Profile"),
            ),
            selectedItem = 0,
            onItemClick = {}
        )
    }
}
