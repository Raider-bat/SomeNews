package com.example.somenews.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.somenews.R
import com.example.somenews.item.NewsItem
import com.example.somenews.db.entity.News
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    private val adapter  = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        news_recyclerview.layoutManager = LinearLayoutManager(this)
        news_recyclerview.adapter = adapter
        (1..20).forEach { i ->
            val new = News(
                "Заголовок новостей${i}",
                null,
                "Какие-то новости на сегодня и т.д.${i}",
                ""
            )
            adapter.add(NewsItem(new))
        }

    }

}
