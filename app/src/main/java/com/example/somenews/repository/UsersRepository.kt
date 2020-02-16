package com.example.somenews.repository

import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val userDao: UserDao) {


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