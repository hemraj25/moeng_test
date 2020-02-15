package com.hemraj.mo_engage.domain

import com.hemraj.mo_engage.Result
import kotlinx.coroutines.flow.Flow


interface Repository {

    suspend fun fetchNewsFeedList(): Flow<Result<List<NewsFeed>>>
}