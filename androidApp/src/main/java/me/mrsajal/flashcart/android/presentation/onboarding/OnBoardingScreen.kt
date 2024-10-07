package me.mrsajal.flashcart.android.presentation.onboarding

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
fun OnBoardingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = {
            navController.navigate(Routes.SignUpEmail.route)
        }) {
            Text(text = "Sign up")
        }
        Button(onClick = { navController.navigate(Routes.LoginEmail.route) }) {
            Text(text = "Login ")
        }
    }
}