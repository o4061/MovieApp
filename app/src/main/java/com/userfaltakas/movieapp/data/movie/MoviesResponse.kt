package com.userfaltakas.movieapp.data.movie

import android.os.Parcelable
import com.userfaltakas.movieapp.data.movie.MovieResult
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesResponse(
    val page: Int = -1,
    val results: List<MovieResult> = emptyList(),
    val total_pages: Int = -1,
    val total_results: Int = -1
) : Parcelable