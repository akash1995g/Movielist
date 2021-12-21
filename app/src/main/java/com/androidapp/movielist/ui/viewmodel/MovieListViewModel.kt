package com.androidapp.movielist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.movielist.data.DataStatus
import com.androidapp.movielist.data.dao.MovieDetails
import com.androidapp.movielist.repository.LoadData
import kotlinx.coroutines.launch

private const val TAG = "MovieListViewModel"

class MovieListViewModel : ViewModel(), MovieResult {

    private val _loadMovieList = MutableLiveData<List<MovieDetails>>()

    init {
        viewModelScope.launch {
            LoadData.getImage(this@MovieListViewModel)
        }

    }

    val getMovieList: LiveData<List<MovieDetails>> = _loadMovieList

    fun updateList() {

    }

    override fun onResult(dataStatus: DataStatus) {
        Log.d(TAG, "onResult: " + dataStatus.movieList.size)
        if (dataStatus.isSuccess) {
            _loadMovieList.postValue(dataStatus.movieList)
        }
    }

}

interface MovieResult {
    fun onResult(dataStatus: DataStatus)
}