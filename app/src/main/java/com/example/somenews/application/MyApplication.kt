package com.example.somenews.application

import android.app.Application
import com.example.somenews.module.databaseModule
import com.example.somenews.module.userViewModelModule
import com.example.somenews.module.usersRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MyApplication)
            modules(listOf(
                userViewModelModule,
                usersRepositoryModule,
                databaseModule
            ))
        }
    }
}