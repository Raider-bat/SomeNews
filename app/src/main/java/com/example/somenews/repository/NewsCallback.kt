package com.example.somenews.repository

import com.example.somenews.model.NewsResponse

interface NewsCallback {

    fun sendResponse(news: NewsResponse)
    
    fun noConnection()
}