package com.androidapp.movielist.networkcalls.api

import com.androidapp.movielist.data.dao.MovieList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallService {

    @GET("3/discover/movie?api_key=8be2f9bca78ccfd1738ea790d3af466e")
    fun getAllMovies(@Query("page") pageNumber: Int = 2): Call<MovieList>

}