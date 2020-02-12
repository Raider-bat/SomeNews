package com.example.somenews.model

import android.widget.ImageView

data class New(
    val header: String,
    val preview: ImageView? = null,
    val date: String
)