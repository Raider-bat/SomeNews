package com.example.somenews.item

import com.example.somenews.R
import com.example.somenews.model.Article
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleItem(private val article: Article) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_article
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textView_dateTime.text = article.publishedAt
        viewHolder.itemView.textView_title.text = article.title
        Picasso.get().load(article.urlToImage).into(viewHolder.itemView.imageView_article)
    }
}