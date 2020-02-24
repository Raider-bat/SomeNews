package com.example.somenews.model

data class LiveResponse<out T>(
    val data: List<T> = emptyList(),
    val isError: Boolean = false
)