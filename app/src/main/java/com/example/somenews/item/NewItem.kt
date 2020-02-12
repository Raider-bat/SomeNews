package com.example.somenews.item

import com.example.somenews.R
import com.example.somenews.model.New
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.new_item.view.*

class NewItem(val new:New): Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textView_heading.text = new.header
        viewHolder.itemView.textView_date.text = new.date

    }

    override fun getLayout(): Int {
        return R.layout.new_item
    }
}