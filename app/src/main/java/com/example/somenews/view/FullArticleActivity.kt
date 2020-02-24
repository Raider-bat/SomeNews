package com.example.somenews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.somenews.R
import com.example.somenews.converter.DateConverter
import com.example.somenews.model.NewsFromAPI
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_article.*

class FullArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_article)
        supportActionBar?.title = "News from Api"

        val article = intent.getParcelableExtra<NewsFromAPI>(ArrayNewsActivity.ARTICLE_KEY)

        setArticleOnView(article)
    }

    private fun setArticleOnView(article: NewsFromAPI) {
        val dateDMY = DateConverter().getDateFormatDMY(article.publishedAt)
        textView_article_publishedAt.text = dateDMY
        textView_article_title.text = article.title
        textView_article_content.text = article.content
        Picasso.get().load(article.urlToImage).into(imageView_article_general_image)
    }
}
