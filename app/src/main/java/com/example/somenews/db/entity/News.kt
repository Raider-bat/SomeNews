package com.example.somenews.db.entity

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    var title: String,
    var urlToImage: String,
    var content: String,
    var date: String,
    var author: String
) {
    @PrimaryKey
    var id: Int = 0
}