package com.example.somenews.view

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.actionmode.ArrayNewsActionMode
import com.example.somenews.db.entity.News
import com.example.somenews.item.ArticleItem
import com.example.somenews.model.LiveResponse
import com.example.somenews.model.NewsFromAPI
import com.example.somenews.viewmodel.NewsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.OnItemLongClickListener
import kotlinx.android.synthetic.main.activity_array_news.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArrayNewsActivity : AppCompatActivity() {

    private val myViewModel: NewsViewModel by viewModel()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    companion object{
        const val ARTICLE_KEY = "ARTICLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array_news)
        supportActionBar?.title = "News from NewsAPI"

        recycler_view_articles.apply {
            layoutManager = LinearLayoutManager(this@ArrayNewsActivity)
            adapter = groupAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@ArrayNewsActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        val articles = myViewModel.getNewsLiveDataFromApi()

        articles.observe(this, Observer<LiveResponse<NewsFromAPI>>{news ->
            Timber.d(news.toString())
            news.data.map {article ->
                if (news.isError){

                    progress_bar_array_news.visibility = ProgressBar.GONE
                    textView_isError.visibility = TextView.VISIBLE
                }else{

                    groupAdapter.add(ArticleItem(article))
                }
            }

            progress_bar_array_news.visibility = ProgressBar.INVISIBLE
        })

        groupAdapter.apply {

            setOnItemClickListener(onItemClickListener)
            setOnItemLongClickListener(onItemLongClickListener)
        }

        val setNewsLiveData =
            ArrayNewsActionMode.setNewsFromAPILiveData
        setNewsLiveData.observe(this, Observer<News>{news ->
            if (ArrayNewsActionMode.mActionMode != null){
                myViewModel.setNewsInLocallyDB(news)
                Toast.makeText(this,"Успешно",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val onItemClickListener = OnItemClickListener { item, _ ->

        val articleData = item as ArticleItem

        if (ArrayNewsActionMode.mActionMode == null){

            val intent = Intent(this, FullArticleActivity::class.java)
            println(articleData.newsFromAPI)
            intent.putExtra(ARTICLE_KEY,articleData.newsFromAPI)
            startActivity(intent)
        }
    }

    private val onItemLongClickListener = OnItemLongClickListener{ item, view ->

        val articleItem = item as ArticleItem
        val article = articleItem.newsFromAPI
        if (ArrayNewsActionMode.mActionMode !=null){

            return@OnItemLongClickListener false
        }else{

            ArrayNewsActionMode.mActionMode =
                startActionMode(
                    ArrayNewsActionMode(
                        article,
                        view
                    )
                )

            return@OnItemLongClickListener true
        }
    }
}
