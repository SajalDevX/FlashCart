package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.mrsajal.flashcart.android.common.theming.Shapes
import me.mrsajal.flashcart.android.navgraph.routes.Routes

@Composable
fun OrderSuccessScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(4.dp, Color.Green, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "Success",
                tint = Color.Green,
                modifier = Modifier.size(40.dp)
            )
        }
        Text(
            text = "Order Confirmed",
            style = MaterialTheme.typography.h6,
            color = Color.Black,
            fontSize = 36.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Your order has been placed successfully.",
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Get delivery between ",
            style = MaterialTheme.typography.body1,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = modifier.height(36.dp))
        TextButton(
            onClick = {
                navController.navigate(Routes.Home.route)
            },
            modifier = modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    Color.Green,
                )
        ) {
            Text(
                "Continue Shopping",
                style = MaterialTheme.typography.button,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSuccessScreenPreview() {
    OrderSuccessScreen(navController = rememberNavController())
}
