package com.example.somenews.service

import com.example.somenews.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/top-headlines")
     fun getNews(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String ): Call<NewsResponse>
}