package com.example.somenews.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.actionmode.FeedAdminActionMode
import com.example.somenews.R
import com.example.somenews.item.LocalNewsItem
import com.example.somenews.viewmodel.NewsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemLongClickListener
import kotlinx.android.synthetic.main.activtiy_feed_admin.*
import org.koin.android.viewmodel.ext.android.viewModel

class FeedAdminActivity : AppCompatActivity() {

    private val adapter  = GroupAdapter<GroupieViewHolder>()
    private val myViewModel: NewsViewModel by viewModel()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item !=null){
            when (item.itemId){
                R.id.menu_add_news_from_locally ->{
                    val intent = Intent(this, AddNewsActivity::class.java)
                    startActivity(intent)
                }

                R.id.menu_add_news_from_api ->{
                    val intent = Intent(this, ArrayNewsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activtiy_feed_admin)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        news_recyclerview.layoutManager = layoutManager

        news_recyclerview.adapter = adapter

        val newsListLiveData = myViewModel.newsLiveDataFromLocally()

        newsListLiveData.observe(this, Observer { newsList ->
            newsList.map { news ->
                println(news)
                adapter.add(LocalNewsItem(news))
            }
        })

        val deleteNewsLiveData = FeedAdminActionMode.deleteNewsLiveData

        deleteNewsLiveData.observe(this, Observer<LocalNewsItem>{newsItem ->
            if (FeedAdminActionMode.mActionMode != null){
                myViewModel.deleteNewsInLocallyDB(newsItem.news)
                adapter.
                    removeGroupAtAdapterPosition(adapter.
                        getAdapterPosition(newsItem))
                Toast.makeText(this,"Новость успешно удалена", Toast.LENGTH_SHORT).show()
            }
        })

        adapter.setOnItemLongClickListener(onItemLongClickListener)
    }

    private val onItemLongClickListener = OnItemLongClickListener{ item, _ ->
        val localeNewsItem = item as LocalNewsItem

        if (FeedAdminActionMode.mActionMode !=null){
            return@OnItemLongClickListener false
        }else{
            FeedAdminActionMode.mActionMode =
                startActionMode(
                    FeedAdminActionMode(
                        localeNewsItem
                    )
                )
            return@OnItemLongClickListener true
        }
    }
}
