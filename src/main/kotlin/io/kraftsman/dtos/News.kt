package io.kraftsman.dtos

data class News(
    val id: String,
    val title: String,
    val author: String,
    val content: String,
    val coverUrl: String,
    val permalink: String,
    val publishedAt: String,
)
