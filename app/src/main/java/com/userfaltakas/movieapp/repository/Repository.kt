package com.userfaltakas.movieapp.repository

import android.content.Context
import com.userfaltakas.movieapp.api.RetrofitInstance
import com.userfaltakas.movieapp.constants.Constants.apiKey
import com.userfaltakas.movieapp.constants.Constants.language
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.movie.MoviesResponse
import com.userfaltakas.movieapp.database.FavoriteMoviesDatabase
import com.userfaltakas.movieapp.database.entities.MovieDB
import retrofit2.Response

class Repository {
    suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse> {
        val filter = mutableMapOf<String, String>()
        filter["api_key"] = apiKey
        filter["language"] = language
        filter["page"] = page.toString()

        return RetrofitInstance.API.getUpcomingMovies(filter)
    }

    suspend fun getPopularMovies(page: Int): Response<MoviesResponse> {
        val filter = mutableMapOf<String, String>()
        filter["api_key"] = apiKey
        filter["language"] = language
        filter["page"] = page.toString()

        return RetrofitInstance.API.getPopularMovies(filter)
    }

    suspend fun getMovieDetails(movieId: Int): Response<MovieDetailResult> {
        val filter = mutableMapOf<String, String>()
        filter["api_key"] = apiKey
        filter["language"] = language

        return RetrofitInstance.API.getMovieDetails(movieId, filter)
    }

    suspend fun getMovieByTitle(movieTitle: String): Response<MoviesResponse> {
        val filter = mutableMapOf<String, String>()
        filter["api_key"] = apiKey
        filter["language"] = language
        filter["query"] = movieTitle

        return RetrofitInstance.API.getMovieByTitle(filter)
    }

    suspend fun getAllMoviesFromDb(context: Context): List<MovieDB> {
        return FavoriteMoviesDatabase.getInstance(context).MovieDao().getAllFavoriteMovies()
    }

    suspend fun removeMovieFromDb(context: Context, movieDB: MovieDB) {
        return FavoriteMoviesDatabase.getInstance(context).MovieDao().removeMovie(movieDB)
    }

    suspend fun addMovieToDb(context: Context, movieDB: MovieDB) {
        return FavoriteMoviesDatabase.getInstance(context).MovieDao().addMovie(movieDB)
    }

    suspend fun checkIfMovieIsSaved(context: Context, movieId: Int): Boolean {
        return FavoriteMoviesDatabase.getInstance(context).MovieDao().checkIfMovieIsSaved(movieId)
    }
}