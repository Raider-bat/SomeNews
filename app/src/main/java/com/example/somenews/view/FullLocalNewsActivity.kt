package com.example.somenews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.somenews.R
import com.example.somenews.converter.DateConverter
import com.example.somenews.db.entity.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_local_news.*

class FullLocalNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_local_news)
        supportActionBar?.title = "Local News"

        val news = intent.getParcelableExtra<News>(FeedActivity.NEWS_KEY)

        setNewsOnView(news)
    }

    private fun setNewsOnView(news: News) {
        val dateDMY = DateConverter().getDateFormatDMY(news.date)
        textView_local_news_publishedAt.text = dateDMY
        textView_local_news_title.text = news.title
        textView_local_news_content.text = news.content
        Picasso.get().load(news.urlToImage).into(imageView_local_news_general_image)
    }
}
