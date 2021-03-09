package com.userfaltakas.movieapp.ui.activity.startPage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPageFactory.StartPageViewModelFactory
import com.userfaltakas.movieapp.ui.fragment.allMoviesFragment.AllMoviesFragment
import com.userfaltakas.movieapp.ui.fragment.detailMovieFragment.DetailMovieFragment

class StartPage : AppCompatActivity(), Communicator, MovieDbCalls, SearchView.OnQueryTextListener {

    private lateinit var repository: Repository
    private lateinit var viewModelFactory: StartPageViewModelFactory
    private lateinit var viewModel: StartPageViewModel

    private lateinit var binding: StartPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository()
        viewModelFactory = StartPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)

        initCall()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.popularMoviesItem -> {
                    getMovies(viewModel, "1", MovieTypeRequest.Popular)
                    binding.searchView.visibility = View.GONE
                }
                R.id.upcomingMovingItem -> {
                    getMovies(viewModel, "1", MovieTypeRequest.Upcoming)
                    binding.searchView.visibility = View.GONE
                }
                R.id.favoriteMoviesItem -> {
                    getMoviesFromDb(viewModel, MovieTypeRequest.Favorites)
                    binding.searchView.visibility = View.GONE
                }
                R.id.searchMoviesItem -> {
                    binding.searchView.visibility = View.VISIBLE
                    binding.frameLayout.removeAllViews()
                    binding.searchView.isSubmitButtonEnabled = true
                    binding.searchView.setOnQueryTextListener(this)
                }
            }
            true
        }
    }


    private fun fragmentTransaction(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().apply {
            remove(fragment)
            replace(R.id.frameLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun goToAllMoviesFragment(
        movies: MoviesResult,
        code: Int,
        movieTypeRequest: MovieTypeRequest
    ) {
        val allMoviesFragment =
            AllMoviesFragment.newInstanceMovies(movies, code, movieTypeRequest)

        fragmentTransaction(allMoviesFragment)
    }

    override fun goToDetailMovieFragment(movie: MovieDetailResult, code: Int) {
        val detailMovieFragment =
            DetailMovieFragment.newInstanceMovieDetails(movie, code)

        fragmentTransaction(detailMovieFragment)
    }


    override fun getMoviesFromDb(
        viewModel: StartPageViewModel,
        movieTypeRequest: MovieTypeRequest
    ) {
        viewModel.getAllMoviesFromDatabase(this)
        val movies = MoviesResult()

        viewModel.favoriteMovies.observe(this, { response ->
            response.forEach {
                movies.add(
                    MovieResult(
                        it.id,
                        it.original_language,
                        it.original_title,
                        it.poster_path,
                        it.release_date,
                        it.vote_average,
                        "",
                        it.genre_ids
                    )
                )
            }
            goToAllMoviesFragment(movies, 0, movieTypeRequest)
        })
    }

    override fun getMovies(
        viewModel: StartPageViewModel,
        movieTitle: String,
        movieTypeRequest: MovieTypeRequest
    ) {
        val movies = MoviesResult()
        var code: Int

        if (NetworkListener().checkNetworkAvailability(this) == NetworkState.CONNECTED) {
            when (movieTypeRequest) {
                MovieTypeRequest.Popular -> viewModel.getPopularMovies(movieTitle.toInt())
                MovieTypeRequest.Upcoming -> viewModel.getUpcomingMovies(movieTitle.toInt())
                MovieTypeRequest.Search -> viewModel.getMoviesByTitle(movieTitle)
            }

            viewModel.movies.observe(this, { response ->
                if (response.isSuccessful) {
                    code = response.code()
                    Log.d("total pages", response.body()?.total_pages.toString())
                    Log.d("total results", response.body()?.total_results.toString())
                    response.body()?.results?.forEach {
                        if (it.poster_path == null || it.backdrop_path == null) {
                            return@forEach
                        }
                        movies.add(
                            MovieResult(
                                it.id,
                                it.original_language,
                                it.original_title,
                                it.poster_path,
                                it.release_date,
                                it.vote_average,
                                it.backdrop_path,
                                it.genre_ids
                            )
                        )
                    }
                    goToAllMoviesFragment(movies, code, movieTypeRequest)
                }
            })
            return
        }
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }


    override fun getMovieDetails(viewModel: StartPageViewModel, movieId: Int) {
        if (NetworkListener().checkNetworkAvailability(this) == NetworkState.CONNECTED) {
            viewModel.getMovieDetails(movieId)
            var movie: MovieDetailResult
            var code: Int

            viewModel.movie.observe(this, { response ->
                if (response.isSuccessful) {
                    code = response.code()
                    response.body()?.let {
                        movie = MovieDetailResult(
                            it.backdrop_path,
                            it.genres,
                            it.id,
                            it.original_language,
                            it.original_title,
                            it.overview,
                            it.popularity,
                            it.release_date,
                            it.runtime,
                            it.poster_path,
                            it.status,
                            it.vote_average
                        )
                        goToDetailMovieFragment(movie, code)
                    }
                }
            })
            return
        }
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            getMovies(viewModel, query, MovieTypeRequest.Search)
            binding.searchView.visibility = View.GONE
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun initCall() {
        if (NetworkListener().checkNetworkAvailability(this) == NetworkState.CONNECTED) {
            getMovies(viewModel, "1", MovieTypeRequest.Popular)
            return
        }
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
    }
}