package com.example.somenews.item

import com.example.somenews.R
import com.example.somenews.converter.DateConverter
import com.example.somenews.db.entity.News
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_article.view.*

class LocalNewsItem(
    val news: News
): Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        val date = DateConverter().getDateFormatDMY(news.date)
        viewHolder.apply {
            itemView.textView_dateTime.text = date
            itemView.textView_title.text = news.title
            Picasso.get().load(news.urlToImage).into(itemView.imageView_article)
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_article
    }
}