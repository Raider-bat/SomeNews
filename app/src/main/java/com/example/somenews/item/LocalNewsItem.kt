package com.example.somenews.item

import com.example.somenews.R
import com.example.somenews.model.Article
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_news.view.*

class LocalNewsItem(private val article: Article): Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textView_date.text = article.publishedAt
        viewHolder.itemView.textView_heading.text = article.title
    }

    override fun getLayout(): Int {
        return R.layout.item_news
    }
}