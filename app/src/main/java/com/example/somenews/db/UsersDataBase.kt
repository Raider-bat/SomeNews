package com.example.somenews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.entity.User

@Database(entities = [User::class],version = 1, exportSchema = false)
abstract class UsersDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}