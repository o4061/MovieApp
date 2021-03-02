package com.userfaltakas.movieapp.data.popularMovies

import android.os.Parcelable
import com.userfaltakas.movieapp.data.movie.MovieResult
import kotlinx.parcelize.Parcelize


@Parcelize
data class PopularMoviesResponse(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
) : Parcelable