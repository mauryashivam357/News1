package com.aman043358.quantumit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    const val BASE_URL = "https://newsapi.org"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    val createNewsAPI : NewsAPI by lazy {
        retrofit.create(NewsAPI::class.java)
    }
}