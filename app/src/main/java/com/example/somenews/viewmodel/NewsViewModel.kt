package com.example.somenews.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somenews.db.entity.News
import com.example.somenews.model.NewsResponse
import com.example.somenews.repository.NewsCallback
import com.example.somenews.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private var newsLiveDataFromApi = MutableLiveData<NewsResponse>()
    private  var newsLiveDataFromLocally = MutableLiveData<List<News>>()

    fun newsLiveDataFromApi(): LiveData<NewsResponse> {
        getNewsFromNewsApi()
        return newsLiveDataFromApi
    }

    fun newsLiveDataFromLocally(): LiveData<List<News>> {
        getNewsFromLocally()
        return newsLiveDataFromLocally
    }

    private fun getNewsFromNewsApi(){
        try {
            repository.getNewsFromApi(object : NewsCallback {
                override fun sendResponse(news: NewsResponse) {
                    newsLiveDataFromApi.value = news
                }
            })
        } catch (e: Exception) {
            println(e)
        }
    }

   private fun getNewsFromLocally() = viewModelScope.launch{
        newsLiveDataFromLocally.value =  repository.getAllNewsFromLocally()
    }
}
