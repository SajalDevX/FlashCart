package me.mrsajal.flashcart.android.common.util.routes

sealed class AuthStreamRoute (val route:String){
    data object Email:AuthStreamRoute("Email")
    data object Mobile:AuthStreamRoute("Mobile")
    data object Details:AuthStreamRoute("Details")
    data object Password:AuthStreamRoute("Password")
}