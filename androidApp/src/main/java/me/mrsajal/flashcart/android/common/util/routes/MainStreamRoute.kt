package me.mrsajal.flashcart.android.common.util.routes

sealed class MainStreamRoute (val route: String){
    data object Home : MainStreamRoute("Home")
    data object Login : MainStreamRoute("Login")
    data object SignUp : MainStreamRoute("SignUp")
    data object Onboarding : MainStreamRoute("Onboarding")
}