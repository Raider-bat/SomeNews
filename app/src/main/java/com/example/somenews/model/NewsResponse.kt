package com.example.somenews.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val articles: List<NewsFromAPI>,
    val status: String,
    val totalResults: Int
) : Parcelable

@Parcelize
data class NewsFromAPI(
    var author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable

@Parcelize
data class Source(
    val id: String,
    val name: String
) : Parcelable