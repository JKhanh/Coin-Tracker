package com.plcoding.cryptotracker

import android.app.Application
import com.jkhanh.shared.di.commonModule
import com.jkhanh.shared.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoTrackerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CryptoTrackerApp)
            androidLogger()
            modules(listOf(commonModule, networkModule))
        }
    }
}