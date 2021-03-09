package com.userfaltakas.movieapp.data.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailResult(
    val backdrop_path: String = "",
    val genres: List<Genre> = emptyList(),
    val id: Int = 0,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val release_date: String = "",
    val runtime: Int = 0,
    val poster_path: String = "",
    val status: String = "",
    val vote_average: Double = 0.0
) : Parcelable