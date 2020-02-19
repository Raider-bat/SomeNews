package com.example.somenews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.somenews.db.dao.NewsDao
import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.entity.News
import com.example.somenews.db.entity.User

@Database(entities = [User::class, News::class],version = 2)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
}