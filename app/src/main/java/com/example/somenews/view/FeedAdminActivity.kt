package com.example.somenews.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.item.NewItem
import com.example.somenews.model.New
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activtiy_feed_admin.*

class FeedAdminActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item !=null){
            when (item.itemId){
                R.id.menu_add_news ->{
                    val intent = Intent(this, AddNewsActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return  super.onOptionsItemSelected(item)
    }


    private val adapter  = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activtiy_feed_admin)

        news_recyclerview.layoutManager = LinearLayoutManager(this)
        news_recyclerview.adapter = adapter
        (1..20).forEach { i ->
            val new = New("Заголовок новостей${i}",null ,"Какие-то новости на сегодня и т.д.${i}" )
            adapter.add(NewItem(new))
        }

    }

}
