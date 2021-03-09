package com.userfaltakas.movieapp.movieDbCalls

import com.userfaltakas.movieapp.data.enums.MovieTypeRequest
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel

interface MovieDbCalls {
    fun getMovies(
        viewModel: StartPageViewModel,
        movieTitle: String,
        movieTypeRequest: MovieTypeRequest
    )

    fun getMovieDetails(viewModel: StartPageViewModel, movieId: Int)
    fun getMoviesFromDb(viewModel: StartPageViewModel, movieTypeRequest: MovieTypeRequest)
}