package com.aman043358.quantumit.api

import com.aman043358.quantumit.datamodel.News
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {
    @GET("/v2/everything?q=apple&from=2023-04-30&to=2023-04-30&sortBy=popularity&apiKey=2f053bee3af2402ab48ca6d617e2d821")
    suspend fun get(): Response<News>
}