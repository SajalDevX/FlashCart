package me.mrsajal.flashcart.di

import me.mrsajal.flashcart.common.data.IOSUserPreferences
import me.mrsajal.flashcart.common.data.createDatastore
import me.mrsajal.flashcart.common.data.local.UserPreferences
import org.koin.dsl.module

actual val platformModule = module {
    single<UserPreferences> { IOSUserPreferences(get()) }

    single {
        createDatastore()
    }
}