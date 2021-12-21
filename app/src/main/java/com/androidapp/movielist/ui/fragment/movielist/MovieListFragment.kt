package com.androidapp.movielist.ui.fragment.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.movielist.R
import com.androidapp.movielist.data.dao.MovieDetails
import com.androidapp.movielist.databinding.FragmentMovieListBinding
import com.androidapp.movielist.ui.fragment.MovieDetailsFragment
import com.androidapp.movielist.ui.viewmodel.MovieListViewModel
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

private const val TAG = "MovieListFragment"

class MovieListFragment : Fragment(), EventListener {

    private var adapter: MovieListAdapter? = null
    private lateinit var mViewModel: MovieListViewModel
    private var _binding: FragmentMovieListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.layoutManager = GridLayoutManager(context, 3)
        binding.recycleView.setItemViewCacheSize(20)

        mViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        mViewModel.getMovieList.observe(viewLifecycleOwner, observer)


        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        mViewModel.updateList()
                    }
                }

            }
        })

        adapter = MovieListAdapter(arrayListOf(), this)
        binding.recycleView.adapter = adapter

        mViewModel.needToShowProgressBar.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: $it")
            if (it != null) {
                if (it) {
                    binding.progressCircular.visibility = View.VISIBLE
                } else {
                    binding.progressCircular.visibility = View.GONE
                }
            }
        })

    }

    private val observer: Observer<List<MovieDetails>> = Observer {
        if (!it.isNullOrEmpty()) {
            adapter?.let { oldList ->
                oldList.list.addAll(it)
                val count = oldList.itemCount
                oldList.notifyItemRangeInserted(count, it.size)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(movieDetails: MovieDetails) {
        val bundle = bundleOf(Pair(MovieDetailsFragment.MOVIE_DETAILS, Gson().toJson(movieDetails)))
        findNavController().navigate(R.id.action_MovieListFragment_to_MovieDetailsFragment, bundle)
    }

}

interface EventListener {
    fun onClick(movieDetails: MovieDetails)
}

