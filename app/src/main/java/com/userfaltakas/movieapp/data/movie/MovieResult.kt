package com.userfaltakas.movieapp.data.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResult(
    val id: Int,
    val original_language: String = "",
    val original_title: String = "",
    var poster_path: String = "",
    val release_date: String = "",
    val vote_average: Double = 0.0,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = emptyList(),
) : Parcelable