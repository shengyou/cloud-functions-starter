package io.kraftsman.dtos

import io.kraftsman.serializers.LocalDateTimeAsStringSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class News(
    val id: String,
    val title: String,
    val author: String,
    val content: String,
    val coverUrl: String?,
    val permalink: String,
    @Serializable(with = LocalDateTimeAsStringSerializer::class)
    val publishedAt: LocalDateTime,
)
