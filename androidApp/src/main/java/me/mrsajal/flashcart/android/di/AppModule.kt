package me.mrsajal.flashcart.android.di

import me.mrsajal.flashcart.android.MainActivityViewModel
import me.mrsajal.flashcart.android.auth.login.LoginViewModel
import me.mrsajal.flashcart.android.auth.signup.SignupViewModel
import me.mrsajal.flashcart.android.presentation.users.customer.cart.CartViewModel
import me.mrsajal.flashcart.android.presentation.users.customer.home.HomeScreenViewModel
import me.mrsajal.flashcart.android.presentation.users.customer.product.ProductDetailViewModel
import me.mrsajal.flashcart.android.presentation.users.customer.wishlist.WishlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { WishlistViewModel(get(), get()) }
    viewModel { CartViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { ProductDetailViewModel(get(), get(), get(), get(), get(), get(), get()) }
}