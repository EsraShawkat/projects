package com.example.level4task2.repository

import android.util.Log
import com.example.level4task2.data.model.api.Api
import com.example.level4task2.data.model.api.util.Resource
import com.example.level4task2.data.model.MovieInformation
import com.example.level4task2.data.model.api.ApiService
import kotlinx.coroutines.withTimeout

class MovieRepository {

    private val _apiService: ApiService = Api.createApi()

    suspend fun getRandomMovie(query: String): Resource<MovieInformation> {
        val apiKey = "4aca32c18af2e870487c9be1c7611da2"

        return try {
            withTimeout(5_000) {
                val response = _apiService.getRandomMovie(apiKey, query)
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("An unknown error occurred")
                }
            }
        } catch (e: Exception) {
            Log.e("MovieRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }
    }
}
