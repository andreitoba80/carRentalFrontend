package com.example.carrentalfrontend

import android.app.Application
import androidx.lifecycle.asLiveData
import com.example.carrentalfrontend.core.repositories.ProtoRepository
import com.example.carrentalfrontend.di.injectFeature
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class CarRentalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@CarRentalApplication)
            injectFeature()
        }
    }
    
    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}