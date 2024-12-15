package com.example.core.data

data class Note(
    var title: String = "",
    var content: String = "",
    var creationTime: Long = 0L,
    var updateTime: Long = 0L,
    var id: Long = 0L,
    var wordCount: Int = 0
)
