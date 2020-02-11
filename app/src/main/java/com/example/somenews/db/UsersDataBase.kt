package com.example.somenews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.entity.User

@Database(entities = [User::class],version = 1)
abstract class UsersDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: UsersDataBase? = null

        fun getInstance(context: Context): UsersDataBase {
            val tempInstance =
                INSTANCE
            if (tempInstance !=null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersDataBase::class.java,
                    "users_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}