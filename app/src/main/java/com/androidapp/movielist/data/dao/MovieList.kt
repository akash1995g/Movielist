package com.androidapp.movielist.data.dao

import com.google.gson.annotations.SerializedName
import java.lang.reflect.Array


data class MovieList(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val moviesString: List<MovieDetails>
)

data class MovieDetails(
    @SerializedName("genre_ids") val genreIds: ArrayList<Int>,
    @SerializedName("title") val title: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val averageVote: Float,
    @SerializedName("vote_count") val voteCount: Float,
    @SerializedName("poster_path") val imagePath: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("overview") val description: String,
    @SerializedName("backdrop_path") val ImageBackDrop: String
)


