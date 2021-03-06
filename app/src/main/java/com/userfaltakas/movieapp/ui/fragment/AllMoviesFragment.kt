package com.userfaltakas.movieapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.userfaltakas.movieapp.communicator.Communicator
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

        fun newInstanceMovies(movies: MoviesResult, code: Int): AllMoviesFragment {
            bundle.apply {
                putParcelable("MOVIES", movies)
                putInt("CODE", code)
            }

            fragment.arguments = bundle
            return fragment
        }

        fun getArgs(): MoviesResult? {
            return fragment.arguments?.getParcelable("MOVIES")
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

        val lambda = { movieId: Int ->
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

        val movies = getArgs()
        val adapter = movies?.let { MovieAdapter(movies, lambda) }

        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}