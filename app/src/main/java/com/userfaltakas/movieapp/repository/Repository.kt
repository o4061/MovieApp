package com.userfaltakas.movieapp.repository

import com.userfaltakas.movieapp.api.RetrofitInstance
import com.userfaltakas.movieapp.constants.Constants.apiKey
import com.userfaltakas.movieapp.constants.Constants.language
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.upcomingMovies.MoviesResponse
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
}