package com.userfaltakas.movieapp.ui.fragment.allMoviesFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.userfaltakas.movieapp.communicator.Communicator
import com.userfaltakas.movieapp.data.enums.MovieTypeRequest
import com.userfaltakas.movieapp.data.enums.NetworkState
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.movie.MoviesResult
import com.userfaltakas.movieapp.databinding.FragmentAllMoviesBinding
import com.userfaltakas.movieapp.network.NetworkListener
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel
import com.userfaltakas.movieapp.ui.activity.startPageFactory.StartPageViewModelFactory
import com.userfaltakas.movieapp.ui.adapter.MovieAdapter

class AllMoviesFragment : Fragment() {
    private var _binding: FragmentAllMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var communicator: Communicator
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: StartPageViewModelFactory
    private lateinit var viewModel: StartPageViewModel

    companion object {
        private val bundle = Bundle()
        private val fragment = AllMoviesFragment()

        fun newInstanceMovies(
            movies: MoviesResult,
            code: Int,
            movieTypeRequest: MovieTypeRequest
        ): AllMoviesFragment {
            bundle.apply {
                putParcelable("MOVIES", movies)
                putInt("CODE", code)
                putParcelable("TYPE_REQUEST", movieTypeRequest)
            }

            fragment.arguments = bundle
            return fragment
        }

        fun getMoviesArg(): MoviesResult? {
            return fragment.arguments?.getParcelable("MOVIES")
        }

        fun getRequestTypeArg(): MovieTypeRequest? {
            return fragment.arguments?.getParcelable("TYPE_REQUEST")
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = activity as Communicator
        repository = Repository()
        viewModelFactory = StartPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)

        val sendMovieToDetailMovieFragment = { movieId: Int ->
            if (context?.let { NetworkListener().checkNetworkAvailability(it) } == NetworkState.CONNECTED) {
                viewModel.getMovieDetails(movieId)
                var code: Int
                var movie: MovieDetailResult

                viewModel.movie.observe(viewLifecycleOwner, { response ->
                    if (response.isSuccessful) {
                        code = response.code()
                        movie = response?.body()!!
                        communicator.goToDetailMovieFragment(movie, code)
                    }
                })
            }
        }

        val movies = getMoviesArg()

        when (getRequestTypeArg()) {
            MovieTypeRequest.Popular -> binding.progressBar.visibility = View.VISIBLE
            MovieTypeRequest.Upcoming -> binding.progressBar.visibility = View.VISIBLE
            MovieTypeRequest.Search -> binding.progressBar.visibility = View.GONE
            MovieTypeRequest.Favorites -> binding.progressBar.visibility = View.GONE
        }
        val adapter =
            movies?.let {

                MovieAdapter(movies, sendMovieToDetailMovieFragment)

            }

        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}