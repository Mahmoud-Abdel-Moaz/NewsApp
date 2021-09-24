package com.mahmoud.newsapp.repository

import com.mahmoud.newsapp.api.RetrofitInstance

class NewsRepository {

    suspend fun
            getBreakingNews() =
        RetrofitInstance.api.getBreakingNews("eg")
}