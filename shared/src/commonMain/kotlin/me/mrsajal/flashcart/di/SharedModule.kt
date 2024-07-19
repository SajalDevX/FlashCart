package me.mrsajal.flashcart.di

import me.mrsajal.flashcart.auth.data.AuthRepositoryImpl
import me.mrsajal.flashcart.auth.data.AuthService
import me.mrsajal.flashcart.auth.domain.repository.AuthRepository
import me.mrsajal.flashcart.auth.domain.usecase.SignInUseCase
import me.mrsajal.flashcart.auth.domain.usecase.SignUpUseCase
import me.mrsajal.flashcart.common.utils.provideDispatcher
import org.koin.dsl.module

private val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    factory { AuthService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilityModule = module {
    factory { provideDispatcher() }
}
fun getSharedModules() = listOf(authModule, utilityModule, platformModule)