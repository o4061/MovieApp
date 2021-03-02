package com.userfaltakas.movieapp.ui.activity.startPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.userfaltakas.movieapp.data.popularMovies.PopularMoviesResponse
import com.userfaltakas.movieapp.data.upcomingMovies.UpcomingMoviesResponse
import com.userfaltakas.movieapp.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class StartPageViewModel(private val repository: Repository) : ViewModel() {

    lateinit var popularMovies: MutableLiveData<Response<PopularMoviesResponse>>
    lateinit var upcomingMovies: MutableLiveData<Response<UpcomingMoviesResponse>>

    fun getPopularMovies(page: Int) {
        popularMovies = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getPopularMovies(page)
            popularMovies.value = response
        }
    }

    fun getUpcomingMovies(page: Int) {
        upcomingMovies = MutableLiveData()
        viewModelScope.launch {
            val response = repository.getUpcomingMovies(page)
            upcomingMovies.value = response
        }
    }
}