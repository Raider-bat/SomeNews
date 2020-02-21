package com.example.somenews.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VerifiedUser(
    var name: String = "",
    var password: String = ""
) {
    @PrimaryKey
    var id = 0
}