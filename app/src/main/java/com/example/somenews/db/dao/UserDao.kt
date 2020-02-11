package com.example.somenews.db.dao

import androidx.room.*
import com.example.somenews.db.entity.User

@Dao
interface UserDao {
    @Query ("SELECT * FROM user WHERE id = :id ")
    suspend fun getById(id : Int): User

    @Query("SELECT * FROM user WHERE name = :name ")
    suspend fun getByName(name: String) : User

    @Insert
    suspend  fun insert(user : User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query ("SELECT * FROM user WHERE name = :name AND password = :password")
    suspend fun checkUser(name: String, password : String) : User
}