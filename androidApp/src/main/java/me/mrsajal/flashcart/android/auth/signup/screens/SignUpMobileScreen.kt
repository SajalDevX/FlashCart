package me.mrsajal.flashcart.android.auth.signup.screens

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.togitech.ccp.component.TogiCountryCodePicker
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.common.CustomButton
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState
import me.mrsajal.flashcart.android.common.theming.AppTheme
import me.mrsajal.flashcart.android.common.util.routes.Routes

@Composable
fun MobileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: SignUpUiState,
    onNavigateToDetailsScreen: () -> Unit,
    @StringRes buttonText: Int,
    onMobileChange: (String,String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.SignUpEmail.route) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = modifier.size(24.dp),
                            contentDescription = null,
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .width(328.dp)
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mobile),
                    contentDescription = null,
                    modifier = Modifier
                        .height(30.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Green)

                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.mobile_heading),
                    modifier = modifier
                        .width(192.dp),
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.mobile_subheading),
                    modifier = modifier
                        .height(40.dp)
                        .width(328.dp),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400)
                )
                Spacer(modifier = modifier.height(24.dp))
                TogiCountryCodePicker(
                    shape = RoundedCornerShape(12.dp),
                    onValueChange = { (code, phone), isValid ->
                        if (isValid) {
                            onMobileChange(code, phone)
                        }
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
            CustomButton(
                text = stringResource(buttonText),
                onClick = onNavigateToDetailsScreen
            )
            println("The user mobile number is ${uiState.phoneNumber} and email is ${uiState.email}")

        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MobileScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        val uiState = remember { SignUpUiState(email = "") }
        MobileScreen(
            navController = navController,
            uiState = uiState,
            onNavigateToDetailsScreen = { /*TODO*/ },
            buttonText = android.R.string.ok,
            onMobileChange = { _, _ -> }
        )
    }
}

