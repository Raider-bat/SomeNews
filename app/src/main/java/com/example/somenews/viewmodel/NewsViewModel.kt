package com.example.somenews.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somenews.db.entity.News
import com.example.somenews.model.LiveResponse
import com.example.somenews.model.NewsFromAPI
import com.example.somenews.model.NewsResponse
import com.example.somenews.repository.NewsCallback
import com.example.somenews.repository.NewsRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private var newsLiveDataFromApi = MutableLiveData<LiveResponse<NewsFromAPI>>()


    fun getNewsLiveDataFromApi(): LiveData<LiveResponse<NewsFromAPI>> {
        Timber.d("tttt")
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
            Timber.d("trycatch")

            repository.getNewsFromApi(object : NewsCallback {
                override fun sendResponse(news: NewsResponse) {
                    Timber.d("callback")

                    newsLiveDataFromApi.value = LiveResponse(news.articles)
                }

                override fun noConnection() {

                    newsLiveDataFromApi.value = LiveResponse(
                        isError = true
                    )
                }
            })
            
        } catch (e: Exception) {
            Timber.d(e)
        }
    }
}
