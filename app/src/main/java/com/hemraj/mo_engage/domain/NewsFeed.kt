package com.hemraj.mo_engage.domain

data class NewsFeed(
    val author: String,
    val title: String,
    val imageUrl: String?,
    val feedUrl: String?,
    val publishedAt: String
)