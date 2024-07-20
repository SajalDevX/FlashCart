package me.mrsajal.flashcart.android.auth.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.mrsajal.flashcart.android.common.theming.AppTheme
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.common.CustomButton
import me.mrsajal.flashcart.android.common.util.components.CustomTextField
import me.mrsajal.flashcart.android.common.util.routes.Routes

@Composable
fun LoginPasswordScreen(
    onLoginButtonClick:()->Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: LoginUiState,
    onNavigateToPasswordScreen: () -> Unit,
    @StringRes buttonText: Int,
    onPasswordChange: (String) -> Unit,
) {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.LoginEmail.route) }) {
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
                    painter = painterResource(id = R.drawable.password),
                    contentDescription = null,
                    modifier = Modifier
                        .height(30.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.Green)

                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.email_heading),
                    modifier = modifier
                        .width(192.dp),
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.email_subheading),
                    modifier = modifier
                        .height(40.dp)
                        .width(328.dp),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400)
                )
                Spacer(modifier = modifier.height(24.dp))
                CustomTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    hint = R.string.password_hint,
                    isSingleLine = true,
                    isPasswordTextField = true,
                    isValid = if(uiState.errorMessage==null) true else false,
                )
                if (uiState.errorMessage != null) {
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            CustomButton(
                text = stringResource(buttonText),
                onClick = onLoginButtonClick
            )
            println("The user email is ${uiState.email}")
        }
    }
    LaunchedEffect(
        key1 = uiState.authSuccess,
        key2 = uiState.errorMessage
    ) {
        if(uiState.authSuccess){
            onNavigateToPasswordScreen()
        }
        if(uiState.errorMessage!=null){
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LoginPasswordScreenPreview() {
    AppTheme {
        val navController = rememberNavController()
        val uiState = remember { LoginUiState(email = "") }
        LoginPasswordScreen(
            navController = navController,
            uiState = uiState,
            onNavigateToPasswordScreen = { /*TODO*/ },
            buttonText = android.R.string.ok,
            onPasswordChange = {},
            onLoginButtonClick = {}
        )
    }
}