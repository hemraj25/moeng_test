package com.hemraj.mo_engage.data

import com.google.gson.annotations.SerializedName
import com.hemraj.mo_engage.domain.NewsFeed

data class NewsFeedApi(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String
) {
    fun toDomain(): List<NewsFeed> {
        val newsFeedList = mutableListOf<NewsFeed>()
        articles.forEach {
            newsFeedList.add(
                NewsFeed(
                    it.author?:"Author MoEngage",
                    it.title?: "Title MoEngage ",
                    it.urlToImage,
                    it.url,
                    it.publishedAt?: "2020-02-10T13:42:00Z"
                )
            )
        }
        return newsFeedList
    }
}

data class Article(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
)

data class Source(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
