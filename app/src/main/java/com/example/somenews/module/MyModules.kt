package com.example.somenews.module

import android.app.Application
import androidx.room.Room
import com.example.somenews.db.UsersDataBase
import com.example.somenews.repository.NewsRepository
import com.example.somenews.repository.UsersRepository
import com.example.somenews.service.NewsService
import com.example.somenews.viewmodel.NewsViewModel
import com.example.somenews.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { NewsViewModel(get()) }
}

val repositoryModule = module {
    single { UsersRepository(get()) }
    single { NewsRepository(get(),get()) }
}

val databaseModule = module {
    fun provideDatabase(application: Application): UsersDataBase =
        Room.databaseBuilder(
            application,
            UsersDataBase::class.java,
            "users_database")
            .fallbackToDestructiveMigration()
            .build()

    fun provideUserDao(dataBase: UsersDataBase) = dataBase.userDao()
    fun provideNewsDao(dataBase: UsersDataBase) = dataBase.newsDao()
    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
    single { provideNewsDao(get()) }
}

val networkModule =  module {

    fun provideRetrofitBuilder()  = Retrofit.Builder()
        .baseUrl("http://newsapi.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    single { provideRetrofitBuilder() }
    single { get<Retrofit>().create(NewsService::class.java) }
}