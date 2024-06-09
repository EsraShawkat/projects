package com.example.level4task2.data.model

import com.google.gson.annotations.SerializedName

data class MovieInformation(
    @SerializedName("results") val results: List<Movie>
)