package com.hemraj.mo_engage

import com.google.gson.Gson
import com.hemraj.mo_engage.data.DataRepository
import com.hemraj.mo_engage.data.LocalDataRepository
import com.hemraj.mo_engage.data.network.NetworkService

object Injection {

    fun getBaseUrl() = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/"

    fun provideDataRepo() = DataRepository(NetworkService.api, LocalDataRepository(MyApplication.getInstance(), Gson()))
}