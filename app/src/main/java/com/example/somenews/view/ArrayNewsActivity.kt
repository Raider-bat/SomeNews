package com.example.somenews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.item.ArticleItem
import com.example.somenews.model.NewsResponse
import com.example.somenews.viewmodel.NewsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_array_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class ArrayNewsActivity : AppCompatActivity() {

    private val myViewModel: NewsViewModel by viewModel()
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array_news)

        recycler_view_articles.layoutManager = LinearLayoutManager(this)
        recycler_view_articles.adapter = adapter

        val articles = myViewModel.newsLiveDataFromApi()

        articles.observe(this, Observer<NewsResponse>{news ->
            news.articles.map {article ->
                adapter.add(ArticleItem(article))
            }
        })
    }
}
