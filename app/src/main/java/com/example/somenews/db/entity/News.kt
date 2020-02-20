package com.example.somenews.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    var title: String,
    var urlToImage: String,
    var content: String,
    var date: String,
    var author: String,
    var url: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}