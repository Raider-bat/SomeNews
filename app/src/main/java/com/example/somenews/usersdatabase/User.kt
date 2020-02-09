package com.example.somenews.usersdatabase

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