package com.userfaltakas.movieapp.ui.activity.startPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.upcomingMovies.MoviesResponse
import com.userfaltakas.movieapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class StartPageViewModel(private val repository: Repository) : ViewModel() {

    lateinit var movies: MutableLiveData<Response<MoviesResponse>>
    lateinit var movie: MutableLiveData<Response<MovieDetailResult>>

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

    fun getMovieDetails(movieId: Int) {
        movie = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId)
            movie.value = response
        }
    }
}