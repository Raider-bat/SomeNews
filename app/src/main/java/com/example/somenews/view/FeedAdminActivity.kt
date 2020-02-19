package com.example.somenews.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activtiy_feed_admin.*

class FeedAdminActivity : AppCompatActivity() {

    private val adapter  = GroupAdapter<GroupieViewHolder>()

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

        news_recyclerview.layoutManager = LinearLayoutManager(this)
        news_recyclerview.adapter = adapter
    }
}
