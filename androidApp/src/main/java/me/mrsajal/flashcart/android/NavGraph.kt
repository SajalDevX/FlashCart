package me.mrsajal.flashcart.android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.mrsajal.flashcart.android.auth.login.LoginEmailScreen
import me.mrsajal.flashcart.android.auth.login.LoginPasswordScreen
import me.mrsajal.flashcart.android.auth.login.LoginViewModel
import me.mrsajal.flashcart.android.auth.signup.SignupViewModel
import me.mrsajal.flashcart.android.auth.signup.screens.EmailScreen
import me.mrsajal.flashcart.android.auth.signup.screens.MobileScreen
import me.mrsajal.flashcart.android.auth.signup.screens.PasswordScreen
import me.mrsajal.flashcart.android.auth.signup.screens.UserDetailScreen
import me.mrsajal.flashcart.android.common.util.routes.Routes
import me.mrsajal.flashcart.android.presentation.home.HomeScreen
import me.mrsajal.flashcart.android.presentation.home.HomeScreenViewModel
import me.mrsajal.flashcart.android.presentation.onboarding.OnBoardingScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    uiState: MainActivityUiState
) {
    val navController = rememberNavController()
    val signUpViewModel: SignupViewModel = koinViewModel()
    val signUpUiState = signUpViewModel.uiState

    val loginViewModel: LoginViewModel = koinViewModel()
    val loginUiState = loginViewModel.uiState

    val homeViewModel: HomeScreenViewModel = koinViewModel()
    val homeUiState = homeViewModel.homeUiState


    if (uiState is MainActivityUiState.Success) {
        val startDestination = if (uiState.currentUser.token.isNotEmpty()) {
            Routes.Home.route
        } else {
            Routes.Onboarding.route
        }
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Routes.SignUpEmail.route) {
                EmailScreen(
                    navController = navController,
                    uiState = signUpUiState,
                    onNavigateToMobileScreen = {
                        if (signUpViewModel.onEmailContinue()) {
                            navController.navigate(Routes.SignUpMobile.route) {
                                popUpTo(Routes.Onboarding.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    onEmailChange = { email -> signUpViewModel.updateEmail(email) },
                    buttonText = R.string.continue_text
                )
            }
            composable(Routes.SignUpMobile.route) {
                MobileScreen(
                    navController = navController,
                    uiState = signUpUiState,
                    onNavigateToDetailsScreen = {
                        if (signUpViewModel.onPhoneNumberContinue()) {
                            navController.navigate(Routes.SignUpDetails.route) {
                                popUpTo(Routes.SignUpEmail.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    onMobileChange = { code, mobile ->
                        signUpViewModel.updatePhoneNumber(
                            code,
                            mobile
                        )
                    },
                    buttonText = R.string.continue_text
                )
            }
            composable(Routes.SignUpDetails.route) {
                UserDetailScreen(
                    navController = navController,
                    uiState = signUpUiState,
                    onNavigateToPasswordScreen = {
                        if (signUpViewModel.onUsernameContinue() && signUpViewModel.onAgeContinue()) {
                            navController.navigate(Routes.SignUpPassword.route) {
                                popUpTo(Routes.SignUpMobile.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    onAgeChange = { age -> signUpViewModel.updateAge(age) },
                    buttonText = R.string.continue_text,
                    onGenderChange = { gender -> signUpViewModel.updateGender(gender) },
                    onRoleChange = { role -> signUpViewModel.updateRole(role) },
                    onNameChange = { name -> signUpViewModel.updateUsername(name) }
                )
            }
            composable(Routes.SignUpPassword.route) {
                PasswordScreen(
                    navController = navController,
                    uiState = signUpUiState,
                    onNavigateToHome = {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.SignUpEmail.route) {
                                inclusive = true
                            }
                        }
                    },
                    onSignUpClick = {
                        if (signUpViewModel.onPasswordContinue()) {
                            signUpViewModel.signUp()
                        }
                    },
                    onPasswordChange = { password -> signUpViewModel.updatePassword(password) },
                    onConfirmPasswordChange = { confirmPassword ->
                        signUpViewModel.updateConfirmPassword(confirmPassword)
                    },
                    buttonText = R.string.signup_button_hint
                )
            }
            composable(Routes.LoginEmail.route) {
                LoginEmailScreen(
                    navController = navController,
                    uiState = loginUiState,
                    onNavigateToPasswordScreen = {
                        if (loginViewModel.onEmailContinue()) {
                            navController.navigate(Routes.LoginPassword.route) {
                                popUpTo(Routes.Onboarding.route) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    buttonText = R.string.continue_text,
                    onEmailChange = { email -> loginViewModel.updateEmail(email) }
                )
            }
            composable(Routes.LoginPassword.route) {
                LoginPasswordScreen(
                    navController = navController,
                    uiState = loginUiState,
                    onNavigateToPasswordScreen = {
                    },
                    onLoginButtonClick = {
                        if (loginViewModel.onPasswordContinue()) {
                            loginViewModel.signIn()
                        }
                    },
                    buttonText = R.string.login_button_hint,
                    onPasswordChange = { password ->
                        loginViewModel.updatePassword(password)
                    }
                )
            }
            composable(Routes.Onboarding.route) {
                OnBoardingScreen(navController = navController)
            }
            composable(Routes.Home.route) {
                HomeScreen(
                    homeUiState
                )
            }
        }
    }
}