package com.mahmoud.newsapp.api

import com.mahmoud.newsapp.pojo.NewsResponse
import com.mahmoud.newsapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    //https://newsapi.org/v2/top-headlines?country=eg&apiKey=63b1f94dad044add871d1e319c630265
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "eg",
        @Query("apiKey")
        apikey: String = API_KEY,
    ): Response<NewsResponse>

}