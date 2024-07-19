package me.mrsajal.flashcart.android.auth.signup.screens

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.components.AuthScreenTemplate
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState

@Composable
fun EmailScreen(
    uiState: SignUpUiState,
    onButtonClick: () -> Unit,
    @StringRes buttonText: Int,
    onEmailChange: (String) -> Unit,
) {
    val textFields = listOf(
        R.string.email_hint to uiState.email,
    )
    AuthScreenTemplate(
        heading = R.string.email_heading,
        subHeading = R.string.email_subheading,
        logo = R.drawable.email,
        textFields = textFields,
        onValueChange = { index, newValue ->
            when (index) {
                0 -> onEmailChange(newValue)
            }
        },
        onButtonClick = { onButtonClick() },
        buttonText = buttonText,
        onNavigateToLogin = {}
    )
}