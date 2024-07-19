package me.mrsajal.flashcart.android.auth.signup.screens

import androidx.compose.runtime.Composable
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.components.AuthScreenTemplate
import me.mrsajal.flashcart.android.auth.signup.SignUpUiState

@Composable
fun UserDetailScreen(
    onButtonClick: () -> Unit,
    buttonText: Int,
    uiState: SignUpUiState,
    onNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onRoleChange: (String) -> Unit,
) {
    val textFields = listOf(
        R.string.password_hint to uiState.password,
        R.string.confirm_password_hint to uiState.confirmPassword,
    )
    AuthScreenTemplate(
        heading = R.string.user_details_heading,
        subHeading = R.string.user_detail_subheading,
        logo = R.drawable.userdetails,
        textFields = textFields,
        onValueChange = { index, newValue ->
                    when (index) {
                        0 -> onNameChange(newValue)
                        1 -> onGenderChange(newValue)
                        2 -> onAgeChange(newValue)
                        3 -> onRoleChange(newValue)
                    }
                },
        onButtonClick = { onButtonClick() },
        buttonText = buttonText,
        onNavigateToLogin = {}
    )
}