package com.userfaltakas.movieapp.movieDbCalls

import com.userfaltakas.movieapp.data.enums.MovieTypeRequest
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel

interface MovieDbCalls {
    fun getMovies(viewModel: StartPageViewModel, movieTypeRequest: MovieTypeRequest)
    fun getBestArtists()
    fun getMovieDetails(viewModel: StartPageViewModel, movieId: Int)
}