package com.example.somenews.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var name: String= "",
    var password: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var  id: Int = 0
}