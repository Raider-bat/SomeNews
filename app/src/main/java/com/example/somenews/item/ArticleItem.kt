package com.example.somenews.item

import com.example.somenews.R
import com.example.somenews.converter.DateConverter
import com.example.somenews.model.NewsFromAPI
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_news.view.*

class ArticleItem(
    val newsFromAPI: NewsFromAPI
) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_news
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val dateDMY = DateConverter().getDateFormatDMY(newsFromAPI.publishedAt)
        viewHolder.apply {
            itemView.textView_dateTime.text = dateDMY
            itemView.textView_title.text = newsFromAPI.title
            Picasso.get().load(newsFromAPI.urlToImage).into(itemView.imageView_article)
        }
    }
}