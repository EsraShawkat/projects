package com.example.level4task2.data.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("release_date") val date: String,
    @SerializedName("vote_average") val votes: Double,
    @SerializedName("overview") val overview: String,
)
