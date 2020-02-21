package com.example.somenews.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.somenews.db.entity.VerifiedUser

@Dao
interface VerifiedUserDao {

    @Query("SELECT * FROM VerifiedUser")
    suspend fun getVerifiedUser(): VerifiedUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend  fun insert(verifiedUser : VerifiedUser)

    @Query("DELETE FROM VerifiedUser WHERE id = :id")
    suspend fun  delete(id:Int)
}