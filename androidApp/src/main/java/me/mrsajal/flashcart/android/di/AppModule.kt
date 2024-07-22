package me.mrsajal.flashcart.android.di

import me.mrsajal.flashcart.android.MainActivityViewModel
import me.mrsajal.flashcart.android.auth.login.LoginViewModel
import me.mrsajal.flashcart.android.auth.signup.SignupViewModel
import me.mrsajal.flashcart.android.presentation.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{LoginViewModel(get())}
    viewModel { MainActivityViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
}