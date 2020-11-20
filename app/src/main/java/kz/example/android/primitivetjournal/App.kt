package kz.example.android.primitivetjournal

import android.app.Application
import androidx.multidex.MultiDex
import kz.example.android.primitivetjournal.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            modules(appModules)
            androidContext(this@App)
        }
    }

}