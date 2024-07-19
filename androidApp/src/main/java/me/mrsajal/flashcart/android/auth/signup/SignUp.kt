package me.mrsajal.flashcart.android.auth.signup

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.mrsajal.flashcart.android.R
import me.mrsajal.flashcart.android.auth.signup.screens.EmailScreen
import me.mrsajal.flashcart.android.auth.signup.screens.MobileScreen
import me.mrsajal.flashcart.android.auth.signup.screens.PasswordScreen
import me.mrsajal.flashcart.android.auth.signup.screens.UserDetailScreen
import me.mrsajal.flashcart.android.common.util.routes.AuthStreamRoute
import me.mrsajal.flashcart.android.common.util.routes.MainStreamRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpNavHost(
    navController: NavController,
    viewModel: SignupViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState

    val authNavController = rememberNavController()
    NavHost(
        navController = authNavController,
        startDestination = AuthStreamRoute.Email.route
    ) {
        composable(AuthStreamRoute.Email.route) {
            EmailScreen(
                uiState = uiState,
                onButtonClick = {
                    authNavController.navigate(AuthStreamRoute.Mobile.route) {
                        popUpTo(MainStreamRoute.Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                onEmailChange = { email -> viewModel.updateEmail(email) },
                buttonText = R.string.continue_text
            )
        }
        composable(AuthStreamRoute.Mobile.route) {
            MobileScreen(
                uiState = uiState,
                onButtonClick = {
                    authNavController.navigate(AuthStreamRoute.Details.route) {
                        popUpTo(AuthStreamRoute.Email.route) {
                            inclusive = true
                        }
                    }
                },
                onMobileChange = { mobile -> viewModel.updateMobile(mobile) },
                buttonText = R.string.continue_text
            )
        }
        composable(AuthStreamRoute.Details.route) {
            UserDetailScreen(
                uiState = uiState,
                onButtonClick = {
                    authNavController.navigate(AuthStreamRoute.Password.route) {
                        popUpTo(AuthStreamRoute.Mobile.route) {
                            inclusive = true
                        }
                    }
                },
                onAgeChange = { age -> viewModel.updateAge(age) },
                buttonText = R.string.continue_text,
                onGenderChange = { gender -> viewModel.updateGender(gender) },
                onRoleChange = { role -> viewModel.updateRole(role) },
                onNameChange = { name -> viewModel.updateUsername(name) }
            )
        }
        composable(AuthStreamRoute.Password.route) {
            PasswordScreen(
                uiState = uiState,
                onButtonClick = {
                    navController.navigate(MainStreamRoute.Home.route) {
                        popUpTo(AuthStreamRoute.Mobile.route) {
                            inclusive = true
                        }
                    }
                },
                onPasswordChange = { password -> viewModel.updatePassword(password) },
                onConfirmPasswordChange = { confirmPassword -> viewModel.updateConfirmPassword(confirmPassword) },
                buttonText = R.string.signup_button_hint
            )
        }
    }

}