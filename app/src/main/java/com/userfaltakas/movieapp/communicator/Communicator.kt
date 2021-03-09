package com.userfaltakas.movieapp.communicator

import com.userfaltakas.movieapp.data.enums.MovieTypeRequest
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.movie.MoviesResult

interface Communicator {
    fun goToAllMoviesFragment(movies: MoviesResult, code: Int, movieTypeRequest: MovieTypeRequest)
    fun goToDetailMovieFragment(movie: MovieDetailResult, code: Int)
}