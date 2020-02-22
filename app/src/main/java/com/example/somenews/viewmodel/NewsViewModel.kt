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
import timber.log.Timber

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private var newsLiveDataFromApi = MutableLiveData<NewsResponse>()


    fun getNewsLiveDataFromApi(): LiveData<NewsResponse> {
        getNewsFromNewsApi()
        return newsLiveDataFromApi
    }

    fun getNewsLiveDataFromLocally(): LiveData<List<News>>
            = repository.getAllNewsFromLocally()


    fun setNewsInLocallyDB(news: News) = viewModelScope.launch {
        repository.setNewsInLocally(news)
    }

    fun deleteNewsInLocallyDB(news: News) = viewModelScope.launch {
        repository.deleteNewsInLocally(news)
    }

    private fun getNewsFromNewsApi(){
        try {
            repository.getNewsFromApi(object : NewsCallback {
                override fun sendResponse(news: NewsResponse) {
                    newsLiveDataFromApi.value = news
                }
            })
            
        } catch (e: Exception) {
            Timber.d(e)
        }
    }
}
