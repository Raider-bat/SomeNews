package com.example.somenews.repository

import android.app.Application
import com.example.somenews.db.UsersDataBase
import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.entity.User
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UsersRepository(application: Application): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var userDao: UserDao

    init {
        val db = UsersDataBase.getInstance(application)
         userDao = db.userDao()
    }

    suspend fun getByName(name:String):User = getByNameBG(name)

    suspend fun insert(user: User) = insertBG(user)

    suspend fun checkUser(name: String,password: String) = checkUserBG(name, password)

    private suspend fun getByNameBG(name: String):User{
        return withContext(Dispatchers.IO){
            userDao.getByName(name)
        }
    }

    private suspend fun insertBG(user:User){
        withContext(Dispatchers.IO){
            userDao.insert(user)
        }
    }

    private suspend fun checkUserBG(name: String, password: String): User{
        return withContext(Dispatchers.IO){
            userDao.checkUser(name, password)
        }
    }
}