package com.example.somenews.repository

import com.example.somenews.db.dao.NewsDao
import com.example.somenews.db.entity.News
import com.example.somenews.model.NewsResponse
import com.example.somenews.service.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository (private val newsService: NewsService, private val newsDao: NewsDao) {

    fun getNewsFromApi(callback: NewsCallback) {
        newsService
            .getNews(
                "techcrunch",
                "a103830c944d4b61bbcfaeb4fd90c808")
            .enqueue(object : Callback<NewsResponse>{
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {}

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    callback.sendResponse(response.body()!!)
                }
            })
    }

    suspend fun getAllNewsFromLocally(): List<News>{
        return withContext(Dispatchers.IO){
             newsDao.getAllNews()
        }
    }

    suspend fun setNewsInLocally(news: News) = newsDao.setNews(news)

    suspend fun deleteNewsInLocally(news: News) = newsDao.deleteNews(news)
}