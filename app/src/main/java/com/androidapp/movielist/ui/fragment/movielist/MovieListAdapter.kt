package com.androidapp.movielist.ui.fragment.movielist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.movielist.R
import com.androidapp.movielist.data.dao.MovieDetails
import com.bumptech.glide.Glide

private const val TAG = "MovieListAdapter"

class MovieListAdapter(
    private val list: List<MovieDetails>,
    private val listener: EventListener
) :
    RecyclerView.Adapter<MovieListAdapter.MovieDetailsView>() {

    private val baseUrl = "https://image.tmdb.org/t/p/w500"

    inner class MovieDetailsView(view: View) : RecyclerView.ViewHolder(view) {
        val posterImage = view.findViewById(R.id.image_view) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsView {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_layout, parent, false)
        return MovieDetailsView(view)
    }

    override fun onBindViewHolder(holder: MovieDetailsView, position: Int) {

        // Glide function to load the image to UI
        Glide.with(holder.itemView).load(baseUrl + list[position].imagePath)
            .into(holder.posterImage)

        holder.posterImage.setOnClickListener {
            // when user clicks on the image send the movie details to new UI
            listener.onClick(list[position])
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}