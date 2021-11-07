package com.jcy.dessertorderapp

import android.app.Application
import android.content.Context
import com.jcy.dessertorderapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DessertOrderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }
    companion object{
        var appContext: Context?= null
        private set
    }
}