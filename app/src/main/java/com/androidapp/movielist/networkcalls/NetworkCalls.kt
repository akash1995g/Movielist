package com.androidapp.movielist.networkcalls

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "APIService"

class NetworkCalls {

    companion object {
        private const val Base_Url = "https://api.themoviedb.org/"
        fun instance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

}