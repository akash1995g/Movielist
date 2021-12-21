package com.androidapp.movielist.repository

import com.androidapp.movielist.data.DataStatus
import com.androidapp.movielist.data.dao.MovieList
import com.androidapp.movielist.networkcalls.NetworkCalls
import com.androidapp.movielist.networkcalls.api.ApiCallService
import com.androidapp.movielist.ui.viewmodel.MovieResult
import com.androidapp.movielist.utils.MovieGenre

private const val TAG = "LoadData"

class LoadData {
    companion object {

        /**
         * getApiInstance is used create a instance for Api Calls
         */
        private fun getApiInstance(): ApiCallService {
            val network = NetworkCalls.instance()
            return network.create(ApiCallService::class.java)
        }

        /**
         * getMovieImageList is used get image list from the Server/Api
         *
         * @param listener will use MovieResult callback to notify the Api result
         * @param page will be use to fetch movie list for specific page
         *
         */
        fun getMovieImageList(listener: MovieResult, page: Int) {

            var dataStatus = DataStatus(false, arrayListOf(), "Unknown Error")

            val call = getApiInstance().getAllMovies(page)

            call.enqueue(object : retrofit2.Callback<MovieList> {
                override fun onResponse(
                    call: retrofit2.Call<MovieList>,
                    response: retrofit2.Response<MovieList>
                ) {
                    if (response.isSuccessful) {

                        val body = response.body()
                        if (body != null) {
                            dataStatus = DataStatus(true, body.moviesString, null)
                        }
                    } else {
                        dataStatus =
                            DataStatus(false, arrayListOf(), response.errorBody()?.string())
                    }
                    listener.onResult(dataStatus)
                }

                override fun onFailure(call: retrofit2.Call<MovieList>, t: Throwable) {
                    dataStatus =
                        DataStatus(false, arrayListOf(), t.message)
                    listener.onResult(dataStatus)
                }

            })
        }

        /**
         *  getGenreNames function will take array of movie ids,
         *  And returns it is Name for each genre id
         *
         *  @param genreId array list containing genres id
         *
         *  @return A Array list with the genre names
         */

        fun getGenreNames(genreId: ArrayList<Int>): ArrayList<String> {
            val genreName = arrayListOf<String>()
            for (i in 0 until genreId.size) {
                if (MovieGenre.genre.containsKey(genreId[i])) {
                    MovieGenre.genre[genreId[i]]?.let { genreName.add(it) }
                } else {
                    genreName.add("Unknown")
                }
            }
            return genreName
        }

    }
}