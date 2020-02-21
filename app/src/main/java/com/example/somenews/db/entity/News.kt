package com.example.somenews.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class News(
    var title: String,
    var urlToImage: String,
    var content: String,
    var date: String,
    var author: String,
    var url: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}