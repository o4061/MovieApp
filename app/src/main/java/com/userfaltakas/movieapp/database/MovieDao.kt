package com.userfaltakas.movieapp.database

import androidx.room.*
import com.userfaltakas.movieapp.database.entities.MovieDB

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: MovieDB)

    @Delete
    suspend fun removeMovie(movie: MovieDB)

    @Query("SELECT EXISTS(SELECT id FROM movie_table WHERE id= :id)")
    suspend fun checkIfMovieIsSaved(id: Int): Boolean

    @Query("SELECT * FROM movie_table")
    suspend fun getAllFavoriteMovies(): List<MovieDB>
}