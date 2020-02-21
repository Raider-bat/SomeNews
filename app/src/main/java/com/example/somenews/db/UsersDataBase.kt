package com.example.somenews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.somenews.db.dao.NewsDao
import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.dao.VerifiedUserDao
import com.example.somenews.db.entity.News
import com.example.somenews.db.entity.User
import com.example.somenews.db.entity.VerifiedUser

@Database(entities = [User::class, News::class, VerifiedUser::class],version = 3 )
abstract class UsersDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsDao(): NewsDao
    abstract fun verifiedUserDao(): VerifiedUserDao
}