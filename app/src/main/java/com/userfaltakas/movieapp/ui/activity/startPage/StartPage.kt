package com.userfaltakas.movieapp.ui.activity.startPage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.userfaltakas.movieapp.R
import com.userfaltakas.movieapp.communicator.Communicator
import com.userfaltakas.movieapp.data.enums.MovieTypeRequest
import com.userfaltakas.movieapp.data.enums.NetworkState
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.movie.MovieResult
import com.userfaltakas.movieapp.data.movie.MoviesResult
import com.userfaltakas.movieapp.databinding.StartPageBinding
import com.userfaltakas.movieapp.movieDbCalls.MovieDbCalls
import com.userfaltakas.movieapp.network.NetworkListener
import com.userfaltakas.movieapp.ui.fragment.AllMoviesFragment
import com.userfaltakas.movieapp.ui.fragment.ChooseActionFragment
import com.userfaltakas.movieapp.ui.fragment.DetailMovieFragment

class StartPage : AppCompatActivity(), Communicator, MovieDbCalls {

    private lateinit var binding: StartPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentTransaction(ChooseActionFragment())
    }


    private fun fragmentTransaction(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().apply {
            remove(fragment)
            replace(R.id.frameLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun goToAllMoviesFragment(movies: MoviesResult, code: Int) {
        val allMoviesFragment =
            AllMoviesFragment.newInstanceMovies(movies, code)

        fragmentTransaction(allMoviesFragment)
    }

    override fun goToDetailMovieFragment(movie: MovieDetailResult, code: Int) {
        val detailMovieFragment =
            DetailMovieFragment.newInstanceMovieDetails(movie, code)

        fragmentTransaction(detailMovieFragment)
    }

    override fun getMovies(
        viewModel: StartPageViewModel,
        movieTypeRequest: MovieTypeRequest
    ) {
        val movies = MoviesResult()
        var code: Int

        if (NetworkListener().checkNetworkAvailability(this) == NetworkState.CONNECTED) {
            when (movieTypeRequest) {
                MovieTypeRequest.Popular -> viewModel.getPopularMovies(1)
                MovieTypeRequest.Upcoming -> viewModel.getUpcomingMovies(1)
            }

            viewModel.movies.observe(this, { response ->
                if (response.isSuccessful) {
                    code = response.code()
                    response.body()?.results?.forEach {
                        movies.add(
                            MovieResult(
                                it.id,
                                it.original_language,
                                it.original_title,
                                it.poster_path,
                                it.release_date,
                                it.vote_average,
                                it.genre_ids
                            )
                        )
                    }
                    goToAllMoviesFragment(movies, code)
                }
            })
        } else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getBestArtists() {
        TODO("Not yet implemented")
    }

    override fun getMovieDetails(viewModel: StartPageViewModel, movieId: Int) {
        if (NetworkListener().checkNetworkAvailability(this) == NetworkState.CONNECTED) {
            viewModel.getMovieDetails(movieId)
            var movie: MovieDetailResult
            var code: Int

            viewModel.movie.observe(this, { response ->
                if (response.isSuccessful) {
                    movie = response?.body()!!
                    code = response?.code()

                    goToDetailMovieFragment(movie, code)
                }
            })
        } else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()
        }
    }
}