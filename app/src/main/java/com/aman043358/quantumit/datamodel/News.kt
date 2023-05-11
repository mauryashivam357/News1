package com.aman043358.quantumit.datamodel

data class News(val articles: ArrayList<Articles>)

data class Articles(

    val source: Source,
    val title: String,
    val description: String,
    val publishedAt: String,
    val urlToImage: String,

    )

data class Source(val name: String)
