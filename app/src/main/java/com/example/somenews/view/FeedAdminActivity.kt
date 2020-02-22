package com.example.somenews.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.actionmode.FeedAdminActionMode
import com.example.somenews.item.LocalNewsItem
import com.example.somenews.viewmodel.NewsViewModel
import com.example.somenews.viewmodel.UserViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.OnItemLongClickListener
import kotlinx.android.synthetic.main.activtiy_feed_admin.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FeedAdminActivity : AppCompatActivity() {

    private val groupAdapter  = GroupAdapter<GroupieViewHolder>()
    private val myViewModel: NewsViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()
    private val newsListHashMap = HashMap<Int,LocalNewsItem>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item !=null){
            when (item.itemId){

                R.id.admin_menu_add_news_from_api ->{
                    val intent = Intent(this, ArrayNewsActivity::class.java)
                    startActivity(intent)
                }
                R.id.admin_menu_sing_out ->{
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
        setContentView(R.layout.activtiy_feed_admin)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true

        news_recyclerview.apply {
            this.layoutManager = layoutManager
            this.adapter = groupAdapter
            this.addItemDecoration(
                DividerItemDecoration(
                    this@FeedAdminActivity,
                DividerItemDecoration.VERTICAL
                )
            )
        }

        val newsListLiveData = myViewModel.getNewsLiveDataFromLocally()

        newsListLiveData.observe(this, Observer { newsList ->
            newsList.map { news ->
                println(news)
                newsListHashMap[news.id] = LocalNewsItem(news)
            }

            groupAdapter.update(newsListHashMap.values)
        })

        val deleteNewsLiveData = FeedAdminActionMode.deleteNewsLiveData

        deleteNewsLiveData.observe(this, Observer<LocalNewsItem>{newsItem ->
            if (FeedAdminActionMode.mActionMode != null){
                myViewModel.deleteNewsInLocallyDB(newsItem.news)
                newsListHashMap.remove(newsItem.news.id)
                Toast.makeText(this,"Новость успешно удалена", Toast.LENGTH_SHORT).show()
            }
        })

        groupAdapter.apply {
            setOnItemLongClickListener(onItemLongClickListener)
            setOnItemClickListener(onItemClickListener)
        }
    }

    private val onItemClickListener = OnItemClickListener{ item, view ->
        val localNewsItem = item as LocalNewsItem

        if (FeedAdminActionMode.mActionMode == null) {
            val intent = Intent(this, FullLocalNewsActivity::class.java)
            intent.putExtra(FeedActivity.NEWS_KEY, localNewsItem.news)
            startActivity(intent)
        }
    }

    private val onItemLongClickListener = OnItemLongClickListener{ itemLongClick, viewLongClick ->

        val localeNewsItem = itemLongClick as LocalNewsItem
        if (FeedAdminActionMode.mActionMode !=null){

            return@OnItemLongClickListener false
        }else{

            FeedAdminActionMode.mActionMode =
                startActionMode(
                    FeedAdminActionMode(
                        localeNewsItem,
                        viewLongClick
                    )
                )

            return@OnItemLongClickListener true
        }
    }
}
