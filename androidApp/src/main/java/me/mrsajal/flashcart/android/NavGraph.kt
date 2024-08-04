package me.mrsajal.flashcart.android

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.mrsajal.flashcart.android.auth.login.LoginEmailScreen
import me.mrsajal.flashcart.android.auth.login.LoginPasswordScreen
import me.mrsajal.flashcart.android.auth.login.LoginViewModel
import me.mrsajal.flashcart.android.auth.signup.SignupViewModel
import me.mrsajal.flashcart.android.auth.signup.screens.EmailScreen
import me.mrsajal.flashcart.android.auth.signup.screens.MobileScreen
import me.mrsajal.flashcart.android.auth.signup.screens.PasswordScreen
import me.mrsajal.flashcart.android.auth.signup.screens.UserDetailScreen
import me.mrsajal.flashcart.android.common.util.routes.Routes
import me.mrsajal.flashcart.android.presentation.components.BottomAppBar
import me.mrsajal.flashcart.android.presentation.components.BottomNavigationItem
import me.mrsajal.flashcart.android.presentation.users.customer.home.HomeScreen
import me.mrsajal.flashcart.android.presentation.users.customer.home.HomeScreenViewModel
import me.mrsajal.flashcart.android.presentation.onboarding.OnBoardingScreen
import me.mrsajal.flashcart.android.presentation.users.customer.cart.CartScreen
import me.mrsajal.flashcart.android.presentation.users.customer.cart.CartViewModel
import me.mrsajal.flashcart.android.presentation.users.customer.product.ProductDetail
import me.mrsajal.flashcart.android.presentation.users.customer.product.ProductDetailScreen
import me.mrsajal.flashcart.android.presentation.users.customer.wishlist.WishlistScreen
import me.mrsajal.flashcart.android.presentation.users.customer.wishlist.WishlistViewModel
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

    val wishlistViewModel: WishlistViewModel = koinViewModel()
    val wishListUiState = wishlistViewModel.uiState

    val cartViewModel: CartViewModel = koinViewModel()
    val cartUiState = cartViewModel.uiState


    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.homeicon, text = "Home"),
            BottomNavigationItem(icon = R.drawable.hearticon, text = "Wishlist"),
            BottomNavigationItem(icon = R.drawable.papericon, text = "Cart"),
            BottomNavigationItem(icon = R.drawable.profileicon, text = "Profile"),
        )
    }

    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Routes.Home.route -> 0
        Routes.Wishlist.route -> 1
        Routes.Cart.route -> 2
        Routes.Profile.route -> 3
        else -> 0
    }
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Routes.Home.route ||
                backStackState?.destination?.route == Routes.Wishlist.route ||
                backStackState?.destination?.route == Routes.Cart.route ||
                backStackState?.destination?.route == Routes.Profile.route
    }


    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            BottomAppBar(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Routes.Home.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Routes.Wishlist.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Routes.Cart.route
                        )

                        3 -> navigateToTab(
                            navController = navController,
                            route = Routes.Profile.route
                        )
                    }
                }
            )
        }
    }) {
        if (uiState is MainActivityUiState.Success) {
            val startDestination = when {
                uiState.currentUser.token.isNotEmpty() && uiState.currentUser.userRole == "customer" -> Routes.Home.route
                uiState.currentUser.token.isNotEmpty() && uiState.currentUser.userRole == "seller" -> Routes.SellerHome.route
                uiState.currentUser.token.isNotEmpty() && uiState.currentUser.userRole == "admin" -> Routes.AdminHome.route
                uiState.currentUser.token.isNotEmpty() && uiState.currentUser.userRole == "super_admin" -> Routes.SuperAdminHome.route

                else -> Routes.Onboarding.route
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
                        onNavigateToHomeScreen = {
                            navController.navigate(Routes.Home.route)
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
                        homeUiState = homeUiState,
                        fetchData = homeViewModel::fetchData,
                        homeRefreshState = homeViewModel.homeRefreshState,
                        navController = navController,
                        onUiAction = homeViewModel::onUiAction
                    )
                }
                composable(Routes.Wishlist.route) {
                    WishlistScreen(
                        uiState = wishListUiState,
                        fetchData = wishlistViewModel::getWishListItems,
                        event = wishlistViewModel::onUiAction
                    )
                }
                composable(Routes.Cart.route) {
                    CartScreen(
                        cartUiState = cartUiState,
                        fetchData = cartViewModel::getCartItems,
                        cartUiAction = cartViewModel::handleAction,
                        fetchAddress = cartViewModel::getAddressData,
                        navigateToProduct = {
                        }
                    )
                }
                composable(
                    route = Routes.ProductDetailScreen.route + "/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.StringType })
                ) {
                    ProductDetail(
                    )
                }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}