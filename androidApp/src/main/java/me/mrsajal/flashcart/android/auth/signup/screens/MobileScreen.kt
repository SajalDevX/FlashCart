package me.mrsajal.flashcart.android.auth.signup.screens

import androidx.compose.runtime.Composable
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.components.AuthScreenTemplate
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState

@Composable
fun MobileScreen(
    onButtonClick: () -> Unit,
    buttonText: Int,
    uiState: SignUpUiState,
    onMobileChange: (String) -> Unit,
) {
    val textFields = listOf(
        R.string.mobile_number_hint to uiState.mobile,
    )
    AuthScreenTemplate(
        heading = R.string.mobile_heading,
        subHeading = R.string.mobile_subheading,
        logo = R.drawable.mobile,
        textFields = textFields,
        onValueChange = { index, newValue ->
            when (index) {
                0 -> onMobileChange(newValue)
            }
        },
        onButtonClick = { onButtonClick() },
        buttonText = buttonText,
        onNavigateToLogin = {}
    )
}