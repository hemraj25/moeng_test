package com.hemraj.mo_engage.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.hemraj.mo_engage.Injection
import com.hemraj.mo_engage.data.NewsFeedApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object NetworkService {

    private val loggingInterceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //okhttp client
    private val httpClient = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .build()

    private fun getRetrofitClient() = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(Injection.getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
        .build()


     // Build Api client
     val api = getRetrofitClient()
         .create(NetworkApi::class.java)

    interface NetworkApi {

        @GET("news-api-feed/staticResponse.json")
        fun fetchNewsFeedList(): Call<NewsFeedApi>
    }
}