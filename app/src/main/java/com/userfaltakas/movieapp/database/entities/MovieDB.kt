package com.userfaltakas.movieapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.userfaltakas.movieapp.data.movie.Genre

@Entity(tableName = "movie_table")
data class MovieDB(
    @PrimaryKey(autoGenerate = false)
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
)
