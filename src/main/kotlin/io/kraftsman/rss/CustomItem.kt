package io.kraftsman.rss

import tw.ktrssreader.annotation.RssRawData
import tw.ktrssreader.annotation.RssTag

@RssTag(name = "item")
data class CustomItem(
    val title: String?,
    @RssRawData(rawTags = ["dc:creator"])
    val author: String?,
    @RssRawData(rawTags = ["description", "content:encoded"])
    val description: String?,
    @RssRawData(rawTags = ["featuredImage"])
    val featuredImage: String?,
    val link: String?,
    val pubDate: String?,
)
