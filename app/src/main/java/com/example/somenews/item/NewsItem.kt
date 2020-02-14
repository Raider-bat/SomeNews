package com.example.somenews.item

import com.example.somenews.R
import com.example.somenews.db.entity.News
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.new_item.view.*

class NewsItem(private val news: News): Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textView_heading.text = news.title
        viewHolder.itemView.textView_date.text = news.date

    }

    override fun getLayout(): Int {
        return R.layout.new_item
    }
}