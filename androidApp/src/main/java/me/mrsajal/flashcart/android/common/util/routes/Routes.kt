package me.mrsajal.flashcart.android.common.util.routes

sealed class Routes (val route: String){
    data object Onboarding: Routes("Onboarding")
    data object SignUpEmail: Routes("SignUpEmail")
    data object SignUpMobile: Routes("SignUpMobile")
    data object SignUpDetails: Routes("SignUpDetails")
    data object SignUpPassword: Routes("SignUpPassword")
    data object LoginEmail: Routes("LoginEmail")
    data object LoginPassword: Routes("LoginPassword")
    data object Home : Routes("Home")
}