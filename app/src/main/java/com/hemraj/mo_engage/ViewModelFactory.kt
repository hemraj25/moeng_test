package com.hemraj.mo_engage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hemraj.mo_engage.domain.Repository
import com.hemraj.mo_engage.presentation.NewsFeedViewModel


class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsFeedViewModel::class.java) ->
                NewsFeedViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}