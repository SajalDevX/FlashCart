package me.mrsajal.flashcart.android.auth.signup.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.common.CustomButton
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState
import me.mrsajal.flashcart.android.common.util.components.CustomTextField
import me.mrsajal.flashcart.android.common.util.routes.AuthStreamRoute

@Composable
fun PasswordScreen(
    navController: NavController,
    authNavController: NavController,
    modifier :Modifier = Modifier,
    uiState: SignUpUiState,
    onNavigateToHome: () -> Unit,
    buttonText: Int,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(AuthStreamRoute.Details.route) }) {
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
                    text = stringResource(id = R.string.create_password),
                    modifier = modifier
                        .width(192.dp),
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    lineHeight = 24.sp
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.create_password_subheading),
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
                    isValid =true,
                    isPasswordTextField = true,
                )
                Spacer(modifier = modifier.height(24.dp))
                CustomTextField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    hint = R.string.confirm_password_hint,
                    isSingleLine = true,
                    isPasswordTextField = true,
                    isValid = if(uiState.errMessage==null) true else false,
                )
                if (uiState.errMessage != null) {
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = uiState.errMessage,
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
            CustomButton(
                text = stringResource(buttonText),
                onClick = onNavigateToHome
            )
            println("The user Details are ${uiState.username} ${uiState.age} ${uiState.gender} ${uiState.userRole}, email is ${uiState.email} phone number is ${uiState.phoneNumber} password is ${uiState.password} confirm password is ${uiState.confirmPassword}")

        }
    }
}