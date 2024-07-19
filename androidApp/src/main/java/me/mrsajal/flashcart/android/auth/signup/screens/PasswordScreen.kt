package me.mrsajal.flashcart.android.auth.signup.screens

import androidx.compose.runtime.Composable
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.components.AuthScreenTemplate
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState

@Composable
fun PasswordScreen(
    uiState: SignUpUiState,
    onButtonClick: () -> Unit,
    buttonText: Int,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
) {
    val textFields = listOf(
        R.string.password_hint to uiState.password,
        R.string.confirm_password_hint to uiState.confirmPassword,
    )
    AuthScreenTemplate(
        heading = R.string.create_password,
        subHeading = R.string.nothing,
        logo = R.drawable.password,
        textFields = textFields,
        onValueChange = { index, newValue ->
            when (index) {
                0 -> onPasswordChange(newValue)
                1 -> onConfirmPasswordChange(newValue)
            }
        },
        onButtonClick = { onButtonClick() },
        buttonText = buttonText,
        onNavigateToLogin = {}
    )
}