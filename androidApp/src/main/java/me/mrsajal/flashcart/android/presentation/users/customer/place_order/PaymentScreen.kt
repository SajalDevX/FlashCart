package me.mrsajal.flashcart.android.presentation.users.customer.place_order

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.navgraph.routes.Routes

@Composable
fun PaymentScreen(
    navController: NavController,
    onPaymentSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                onPaymentSuccess()
                navController.navigate(Routes.OrderSuccessScreen.route){
                    popUpTo(Routes.CheckoutScreen.route) { inclusive = true }

                }
            }
        ) {
            Text("Pay now")
        }
    }
}