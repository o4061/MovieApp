package com.userfaltakas.movieapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val original_language: String,
    val original_title: String,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double,
    val genre_ids: Int,
)
