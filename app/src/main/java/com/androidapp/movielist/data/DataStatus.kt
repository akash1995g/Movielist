package com.androidapp.movielist.data

import com.androidapp.movielist.data.dao.MovieDetails

data class DataStatus(
    val isSuccess: Boolean,
    val movieList: List<MovieDetails> = arrayListOf(),
    val onError: String?
)