package me.mrsajal.flashcart.android.di

import me.mrsajal.flashcart.android.auth.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignupViewModel(get()) }
}