package com.androidapp.movielist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidapp.movielist.data.DataStatus
import com.androidapp.movielist.data.dao.MovieDetails
import com.androidapp.movielist.repository.LoadData
import com.androidapp.movielist.utils.GlobalContext
import com.androidapp.movielist.utils.Utils
import kotlinx.coroutines.launch

private const val TAG = "MovieListViewModel"

class MovieListViewModel : ViewModel(), MovieResult {

    private val _loadMovieList = MutableLiveData<List<MovieDetails>>()
    private val _needToShowProgressBar = MutableLiveData<Boolean>()
    private var _pageCount = 1

    init {
        viewModelScope.launch {
            _pageCount = 1
            _needToShowProgressBar.postValue(true)
            LoadData.getMovieImageList(this@MovieListViewModel, _pageCount)
        }

    }


    val getMovieList: LiveData<List<MovieDetails>> = _loadMovieList

    val needToShowProgressBar: LiveData<Boolean> = _needToShowProgressBar

    fun updateList() {
        needToShowProgressBar.let {
            if (it.value != true) {
                viewModelScope.launch {
                    _needToShowProgressBar.postValue(true)
                    LoadData.getMovieImageList(this@MovieListViewModel, _pageCount)
                }
            }
        }

    }

    override fun onResult(dataStatus: DataStatus) {

        if (dataStatus.isSuccess) {

            if (_pageCount == 1) {
                _loadMovieList.postValue(dataStatus.movieList)
            } else {
                _loadMovieList.also {
                    val oldData = getMovieList.value?.toMutableList()
                    oldData?.addAll(dataStatus.movieList)
                    it.postValue(oldData)
                }
            }
            _pageCount++
        } else {
            Log.d(TAG, "onResult: " + dataStatus.onError)
            Utils.showToast(GlobalContext.applicationContext(), "Failed to load the new Data")
        }

        _needToShowProgressBar.postValue(false)
    }

}

interface MovieResult {
    fun onResult(dataStatus: DataStatus)
}