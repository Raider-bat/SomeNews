package com.example.somenews.module

import android.app.Application
import androidx.room.Room
import com.example.somenews.db.UsersDataBase
import com.example.somenews.repository.UsersRepository
import com.example.somenews.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userViewModelModule = module {
    viewModel { UserViewModel(get()) }
}

val usersRepositoryModule = module {
    single { UsersRepository(get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): UsersDataBase =
        Room.databaseBuilder(application,
            UsersDataBase::class.java,
            "users_database").build()

    fun provideDao(dataBase: UsersDataBase) = dataBase.userDao()

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}