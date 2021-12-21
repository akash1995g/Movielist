package com.androidapp.movielist.ui.fragment

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.androidapp.movielist.R
import com.androidapp.movielist.data.dao.MovieDetails
import com.androidapp.movielist.databinding.MovieDetailsFragmentBinding
import com.androidapp.movielist.repository.LoadData
import com.androidapp.movielist.utils.Utils
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
                detailsLayout.releaseDate.text = "Release on : " + movieDetails!!.releaseDate
                detailsLayout.movieTitle.also {
                    it.text = movieDetails!!.title
                    it.setTextColor(Color.WHITE)
                }
                detailsLayout.ratingText.text = movieDetails!!.averageVote.toString()

                val genre = LoadData.getGenreNames(movieDetails!!.genreIds)
                for (name in 0 until genre.size) {
                    val textView = TextView(context)
                    textView.text = genre[name]
                    textView.setTextColor(Color.WHITE)
                    textView.textSize = 8F
                    textView.setTypeface(textView.typeface, Typeface.BOLD_ITALIC)

                    textView.background = context?.let { it1 ->
                        ContextCompat.getDrawable(
                            it1,
                            R.drawable.round_shape
                        )
                    }
                    detailsLayout.grid.addView(textView)
                }

            }

            binding.bookNow.setOnClickListener {
                Utils.showToast(context, "Need to implement this function")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}