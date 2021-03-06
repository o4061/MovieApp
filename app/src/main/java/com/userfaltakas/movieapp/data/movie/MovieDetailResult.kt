package com.userfaltakas.movieapp.data.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailResult(
    val backdrop_path: String,
    val genres: List<Genre>,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val vote_average: Double,
) : Parcelable