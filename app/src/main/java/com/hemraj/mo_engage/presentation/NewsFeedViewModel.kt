package com.hemraj.mo_engage.presentation

import android.util.Log
import androidx.lifecycle.*
import com.hemraj.mo_engage.Result
import com.hemraj.mo_engage.domain.NewsFeed
import com.hemraj.mo_engage.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class NewsFeedViewModel(private val repository: Repository) : ViewModel() {

    private val TAG = NewsFeedViewModel::class.java.canonicalName

    private var fetchNewsFeed = MutableLiveData<Boolean>()

    private val newsFeedResultLiveData: LiveData<Result<List<NewsFeed>>> = fetchNewsFeed.switchMap {
        liveData(Dispatchers.IO) {
            try {
                emit(Result.loading())
                Log.d(TAG, "newsFeedResultLiveData")
                repository.fetchNewsFeedList().collect {
                    emit(it)
                }
            } catch (ioException: Exception) {
                emit(Result.error(ioException))
                Log.d("Error", ioException.message ?: "")
            }
        }
    }

    fun fetchNewsList() {
        fetchNewsFeed.value = true
    }

    fun getNewsList(): LiveData<Result<List<NewsFeed>>>  {
        return newsFeedResultLiveData
    }
}