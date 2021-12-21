package com.androidapp.movielist.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidapp.movielist.data.dao.MovieDetails
import com.androidapp.movielist.databinding.MovieDetailsFragmentBinding
import com.bumptech.glide.Glide
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
private const val TAG = "MovieDetailsFragment"

class MovieDetailsFragment : Fragment() {
    companion object {
        const val MOVIE_DETAILS = "MovieDetails"
    }

    private var _binding: MovieDetailsFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var movieDetails: MovieDetails? = null
    private val baseUrl = "https://image.tmdb.org/t/p/w500"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arg = arguments?.getString(MOVIE_DETAILS)
        arg?.let {
            movieDetails =
                Gson().fromJson(it, MovieDetails::class.java)
            Log.d(TAG, "onCreate: " + movieDetails?.title)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI(view)

    }

    private fun updateUI(view: View) {
        movieDetails?.let {

            Glide.with(view).load(baseUrl + movieDetails!!.imagePath).into(binding.movieImage)

            binding.apply {
                detailsLayout.movieReviews.text = "Reviews : " + movieDetails!!.voteCount
                detailsLayout.movieRating.rating = movieDetails!!.averageVote
                movieDescription.text = movieDetails!!.description
                detailsLayout.releaseDate.text = "R : " + movieDetails!!.releaseDate
                detailsLayout.movieTitle.also {
                    it.text = movieDetails!!.title
                    it.setTextColor(Color.WHITE)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}