package com.example.somenews.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.db.entity.News
import com.example.somenews.item.LocalNewsItem
import com.example.somenews.model.NewsResponse
import com.example.somenews.viewmodel.NewsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_feed.*
import org.koin.android.viewmodel.ext.android.viewModel

class FeedActivity : AppCompatActivity() {

    private val myViewModel : NewsViewModel by viewModel()
    private val adapter  = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        news_recyclerview.layoutManager = LinearLayoutManager(this)
        news_recyclerview.adapter = adapter

        val newsListLiveData = myViewModel.newsLiveDataFromLocally()

        newsListLiveData.observe(this, Observer { newsList ->
            newsList.map { news ->
                adapter.add(LocalNewsItem(news))
            }
        })
    }
}
