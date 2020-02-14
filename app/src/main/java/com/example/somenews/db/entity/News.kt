package com.example.somenews.db.entity

import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    var title: String,
    var preview: ImageView? = null,
    var content: String,
    var date: String
) {
    @PrimaryKey
    var id: Int = 0
}