package com.example.somenews.repository

import androidx.lifecycle.LiveData
import com.example.somenews.db.dao.NewsDao
import com.example.somenews.db.entity.News
import com.example.somenews.model.NewsResponse
import com.example.somenews.service.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class NewsRepository (private val newsService: NewsService, private val newsDao: NewsDao) {

    fun getNewsFromApi(callback: NewsCallback) {
        newsService
            .getNews(
                "techcrunch",
                "a103830c944d4b61bbcfaeb4fd90c808")
            .enqueue(object : Callback<NewsResponse>{
                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Timber.d(t.message)
                    callback.noConnection()
                }

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    Timber.d(response.toString())
                    callback.sendResponse(response.body()!!)
                }
            })
    }

     fun getAllNewsFromLocally(): LiveData<List<News>>{

        return newsDao.getAllNews()
    }

    suspend fun setNewsInLocally(news: News) = newsDao.setNews(news)

    suspend fun deleteNewsInLocally(news: News) = newsDao.deleteNews(news)
}