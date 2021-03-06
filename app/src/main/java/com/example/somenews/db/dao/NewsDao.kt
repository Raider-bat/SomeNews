package com.example.somenews.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.somenews.db.entity.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM NEWS")
     fun getAllNews(): LiveData<List<News>>

    @Insert
    suspend fun setNews(news: News)

    @Delete
    suspend fun deleteNews(news: News)
}