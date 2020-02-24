package com.example.somenews.repository

import com.example.somenews.db.dao.UserDao
import com.example.somenews.db.dao.VerifiedUserDao
import com.example.somenews.db.entity.User
import com.example.somenews.db.entity.VerifiedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(
    private val userDao: UserDao,
    private val verifiedUserDao: VerifiedUserDao
    ) {

    suspend fun getByName(name:String):User
            = getByNameBG(name)

    suspend fun insert(user: User)
            = insertBG(user)

    suspend fun insertVerifiedUser(verifiedUser: VerifiedUser)
            = insertVerifiedUserBG(verifiedUser)

    suspend fun deleteVerifiedUser()
            = deleteVerifiedUserBG()

    suspend fun getVerifiedUser() : VerifiedUser?
            = getVerifiedUserBG()


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

    private suspend fun insertVerifiedUserBG(verifiedUser: VerifiedUser){
        withContext(Dispatchers.IO){
            verifiedUserDao.insert(verifiedUser)
        }
    }

    private suspend fun deleteVerifiedUserBG(){
        withContext(Dispatchers.IO){
            verifiedUserDao.delete(0)
        }
    }

    private suspend fun getVerifiedUserBG(): VerifiedUser?{
        return withContext(Dispatchers.IO){
            verifiedUserDao.getVerifiedUser()
        }
    }
}