package com.example.somenews.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.item.LocalNewsItem
import com.example.somenews.viewmodel.NewsViewModel
import com.example.somenews.viewmodel.UserViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.activity_feed.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FeedActivity : AppCompatActivity() {

    private val myViewModel : NewsViewModel by viewModel()
    private val userViewModel : UserViewModel by viewModel()
    private val groupAdapter  = GroupAdapter<GroupieViewHolder>()
    private val newsListHashMap = HashMap<Int,LocalNewsItem>()

    companion object{

        const val NEWS_KEY = "NEWS_KEY"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item !=null){
            when (item.itemId){

                R.id.user_menu_sing_out ->{
                    userViewModel.deleteVerifiedUser()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        return  super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val newsListFromLocallyLiveData
                = myViewModel.getNewsLiveDataFromLocally()

        news_recyclerview.apply {

            layoutManager = LinearLayoutManager(this@FeedActivity)
            this.adapter = groupAdapter
            addItemDecoration(
                DividerItemDecoration(
                this@FeedActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        newsListFromLocallyLiveData.observe(this, Observer { newsList ->
            newsList.map { news ->
                println(news)
                newsListHashMap[news.id] = LocalNewsItem(news)
            }

            groupAdapter.update(newsListHashMap.values)
        })

        groupAdapter.setOnItemClickListener(onItemClickListener)
    }

    private val onItemClickListener = OnItemClickListener{ item, _ ->
        val localNewsItem = item as LocalNewsItem

        val intent = Intent(this, FullLocalNewsActivity::class.java)
        intent.putExtra(NEWS_KEY, localNewsItem.news)
        startActivity(intent)
    }
}
