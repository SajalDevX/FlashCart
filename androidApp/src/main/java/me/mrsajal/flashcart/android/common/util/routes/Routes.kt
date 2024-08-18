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
    data object Wishlist : Routes("Wishlist")
    data object ProductDetailScreen : Routes("ProductDetailScreen")
    data object Profile : Routes("Profile")
    data object EditProfile : Routes("EditProfile")
    data object Cart : Routes("Cart")
    data object Explore : Routes("Explore")
    data object AddressMainScreen : Routes("AddressMainScreen")
    data object CheckoutScreen : Routes("CheckoutScreen")
    data object OrderSuccessScreen : Routes("OrderSuccessScreen")



    data object AdminHome : Routes("AdminHome")
    data object SellerHome : Routes("SellerHome")
    data object SuperAdminHome : Routes("SuperAdminHome")
}