package com.userfaltakas.movieapp.ui.fragment.detailMovieFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.userfaltakas.movieapp.R
import com.userfaltakas.movieapp.communicator.Communicator
import com.userfaltakas.movieapp.constants.Constants
import com.userfaltakas.movieapp.data.enums.ButtonStatus
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.database.entities.MovieDB
import com.userfaltakas.movieapp.databinding.FragmentDetailMovieBinding
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel
import com.userfaltakas.movieapp.ui.activity.startPageFactory.StartPageViewModelFactory

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteButtonStatus: ButtonStatus

    private lateinit var communicator: Communicator
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: StartPageViewModelFactory
    private lateinit var viewModel: StartPageViewModel

    companion object {
        private val bundle = Bundle()
        private val fragment = DetailMovieFragment()

        fun newInstanceMovieDetails(movie: MovieDetailResult, code: Int): DetailMovieFragment {
            bundle.apply {
                putParcelable("MOVIE", movie)
                putInt("CODE", code)
            }

            fragment.arguments = bundle
            return fragment
        }

        fun getArgs(): MovieDetailResult? {
            return fragment.arguments?.getParcelable("MOVIE")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = activity as Communicator
        repository = Repository()
        viewModelFactory = StartPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)

        val movie = getArgs()

        if (movie != null) {
            initView(movie)
        }

        binding.imageButtonAddToFavorite.setOnClickListener {
            movie?.let { it1 -> addAndRemoveMovieFromDb(it1) }
        }

        binding.imageButtonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.imageButtonRateThis.setOnClickListener {
            rateMovie()
        }
    }

    private fun runtimeFormat(runtime: Int?): String {
        val hours = runtime?.div(60)
        val minutes = runtime?.rem(60)

        return hours.toString() + "h " + minutes.toString() + "m"
    }

    private fun addAndRemoveMovieFromDb(movie: MovieDetailResult) {
        val movieDB: MovieDB

        context?.let { it1 -> viewModel.getAllMoviesFromDatabase(it1) }

        movie.let {
            movieDB = MovieDB(
                it.id,
                it.original_language,
                it.original_title,
                it.poster_path,
                it.release_date,
                it.vote_average,
                it.genres[0].id
            )
            if (favoriteButtonStatus == ButtonStatus.Pressed) {
                context?.let { it1 -> viewModel.removeMovieFromDatabase(it1, movieDB) }
                binding.imageButtonAddToFavorite.setImageResource(R.drawable.favorite_border_white)
                favoriteButtonStatus = ButtonStatus.Unpressed
            } else if (favoriteButtonStatus == ButtonStatus.Unpressed) {
                context?.let { it1 -> viewModel.addMovieToDatabase(it1, movieDB) }
                binding.imageButtonAddToFavorite.setImageResource(R.drawable.favorite_white)
                favoriteButtonStatus = ButtonStatus.Pressed
            }
        }
    }

    private fun rateMovie() {
        binding.frameLayoutForRating.visibility = View.VISIBLE

        binding.buttonApplyRating.setOnClickListener {
            val rating = binding.ratingBar.rating * 2
            Toast.makeText(context, rating.toString(), Toast.LENGTH_SHORT).show()
            binding.frameLayoutForRating.visibility = View.INVISIBLE
        }
    }

    private fun initView(movie: MovieDetailResult) {
        movie.let { it ->
            Picasso.with(context).load(Constants.backdrop_url + it.backdrop_path)
                .into(binding.imageViewBackDrop)

            context?.let { it1 -> viewModel.isMovieSavedInDatabase(it1, it.id) }

            viewModel.isMovieSavedInDatabase.observe(viewLifecycleOwner, {
                favoriteButtonStatus = if (it) {
                    binding.imageButtonAddToFavorite.setImageResource(R.drawable.favorite_white)
                    ButtonStatus.Pressed
                } else {
                    binding.imageButtonAddToFavorite.setImageResource(R.drawable.favorite_border_white)
                    ButtonStatus.Unpressed
                }
            })

            binding.textViewDetailMovieTitle.text = it.original_title
            binding.textViewOverView.text = it.overview
            binding.textViewScore.text = it.vote_average.toString()
            binding.textViewPopularity.text = it.popularity.toInt().toString()
            binding.textViewRuntime.text = runtimeFormat(it.runtime)
            binding.textViewStatus.text = it.status

            if (it.genres.isNotEmpty()) {
                binding.textViewGenre1.text = it.genres[0].name
                binding.textViewGenre1.visibility = View.VISIBLE
            }
            if (it.genres.size >= 2) {
                binding.textViewGenre2.text = it.genres[1].name
                binding.textViewGenre2.visibility = View.VISIBLE
            }
            if (it.genres.size >= 3) {
                binding.textViewGenre3.text = it.genres[2].name
                binding.textViewGenre3.visibility = View.VISIBLE
            }
            if (it.genres.size == 4) {
                binding.textViewGenre4.text = it.genres[3].name
                binding.textViewGenre4.visibility = View.VISIBLE
            }
        }
    }
}