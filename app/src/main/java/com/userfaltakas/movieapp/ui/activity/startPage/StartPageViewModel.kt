package com.userfaltakas.movieapp.ui.activity.startPage

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.movie.MovieResult
import com.userfaltakas.movieapp.data.movie.MoviesResponse
import com.userfaltakas.movieapp.data.movie.MoviesResult
import com.userfaltakas.movieapp.database.entities.MovieDB
import com.userfaltakas.movieapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class StartPageViewModel(private val repository: Repository) : ViewModel() {

    lateinit var movies: MutableLiveData<Response<MoviesResponse>>
    lateinit var favoriteMovies: MutableLiveData<MoviesResult>
    lateinit var movie: MutableLiveData<Response<MovieDetailResult>>
    lateinit var isMovieSavedInDatabase: MutableLiveData<Boolean>

    fun getPopularMovies(page: Int) {
        movies = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getPopularMovies(page)
            movies.value = response
        }
    }

    fun getUpcomingMovies(page: Int) {
        movies = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getUpcomingMovies(page)
            movies.value = response
        }
    }

    fun getMoviesByTitle(movieTitle: String) {
        movies = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getMovieByTitle(movieTitle)
            movies.value = response
        }
    }

    fun getMovieDetails(movieId: Int) {
        movie = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId)
            movie.value = response
        }
    }

    fun addMovieToDatabase(context: Context, movie: MovieDB) {
        viewModelScope.launch {
            repository.addMovieToDb(context, movie)
        }
    }

    fun removeMovieFromDatabase(context: Context, movie: MovieDB) {
        viewModelScope.launch {
            repository.removeMovieFromDb(context, movie)
        }
    }

    fun isMovieSavedInDatabase(context: Context, movieId: Int) {
        isMovieSavedInDatabase = MutableLiveData()
        viewModelScope.launch {
            val response = repository.checkIfMovieIsSaved(context, movieId)
            isMovieSavedInDatabase.value = response
        }
    }

    fun getAllMoviesFromDatabase(context: Context) {
        favoriteMovies = MutableLiveData()
        val moviesResult = MoviesResult()

        viewModelScope.launch {
            val response = repository.getAllMoviesFromDb(context)
            response.forEach {
                val list = listOf(it.genre_ids)
                moviesResult.add(
                    MovieResult(
                        it.id,
                        it.original_language,
                        it.original_title,
                        it.poster_path,
                        it.release_date,
                        it.vote_average,
                        "",
                        list
                    )
                )
            }
            favoriteMovies.value = moviesResult
        }
    }
}