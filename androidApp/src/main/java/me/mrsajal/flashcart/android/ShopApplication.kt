package me.mrsajal.flashcart.android

import android.app.Application
import me.mrsajal.flashcart.android.di.appModule
import me.mrsajal.flashcart.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class ShopApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ShopApplication)
            modules(appModule+getSharedModules())
        }
    }
}