package com.hemraj.mo_engage.data

import com.hemraj.mo_engage.data.network.NetworkService
import com.hemraj.mo_engage.domain.NewsFeed
import com.hemraj.mo_engage.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.hemraj.mo_engage.Result


class DataRepository(
    private val api: NetworkService.NetworkApi,
    private val localDataRepository: LocalDataRepository) : Repository {

    override suspend fun fetchNewsFeedList(): Flow<Result<List<NewsFeed>>> =
        flow {
            try {
                emit(Result.success(localDataRepository.newsFeedFromLocal))
                val result = api.fetchNewsFeedList().execute()
                if (result.isSuccessful) {
                    emit(Result.success(result.body()!!.toDomain()))
                    localDataRepository.addNewsFeedToLocal(result.body()!!.toDomain())
                } else {
                    emit(Result.error(Exception("something went wrong")))
                }
            } catch (e: Exception) {
                emit(Result.error(e))
            }
        }

}