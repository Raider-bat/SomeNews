package com.example.somenews.application

import android.app.Application
import com.example.somenews.BuildConfig
import com.example.somenews.module.databaseModule
import com.example.somenews.module.networkModule
import com.example.somenews.module.repositoryModule
import com.example.somenews.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        startKoin{
            androidContext(this@MyApplication)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                databaseModule,
                networkModule
            ))
        }
    }
}