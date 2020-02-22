package com.example.somenews.converter

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    fun getDateFormatDMY(oldDate : String): String{

        val oldDateFormal = SimpleDateFormat("yyyy-MM-d'T'HH:mm:ss'Z'", Locale.getDefault())
        val date = oldDateFormal.parse(oldDate)
        return SimpleDateFormat("d MMMM y, HH:mm", Locale.getDefault()).format(date)
    }
}