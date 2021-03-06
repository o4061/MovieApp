package com.userfaltakas.movieapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.userfaltakas.movieapp.communicator.Communicator
import com.userfaltakas.movieapp.data.enums.MovieTypeRequest
import com.userfaltakas.movieapp.data.movie.MoviesResult
import com.userfaltakas.movieapp.databinding.FragmentChooseActionBinding
import com.userfaltakas.movieapp.movieDbCalls.MovieDbCalls
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel
import com.userfaltakas.movieapp.ui.activity.startPageFactory.StartPageViewModelFactory

class ChooseActionFragment : Fragment() {
    private var _binding: FragmentChooseActionBinding? = null
    private val binding get() = _binding!!

    private lateinit var communicator: Communicator
    private lateinit var movieDbCalls: MovieDbCalls
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: StartPageViewModelFactory
    private lateinit var viewModel: StartPageViewModel

    companion object {
        fun newInstance(): Fragment {
            return AllMoviesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDbCalls = activity as MovieDbCalls
        communicator = activity as Communicator
        repository = Repository()
        viewModelFactory = StartPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)


        binding.Artists.setOnClickListener {

        }

        binding.Favorites.setOnClickListener {

        }

        binding.Popular.setOnClickListener {
            movieDbCalls.getMovies(viewModel, MovieTypeRequest.Popular)
        }

        binding.Upcoming.setOnClickListener {
            movieDbCalls.getMovies(viewModel, MovieTypeRequest.Upcoming)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}