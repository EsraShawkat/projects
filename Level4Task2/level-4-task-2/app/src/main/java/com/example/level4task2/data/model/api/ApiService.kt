package com.example.level4task2.data.model.api

import com.example.level4task2.data.model.MovieInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    suspend fun getRandomMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Response<MovieInformation>
}
