package com.userfaltakas.movieapp.api

import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.data.movie.MoviesResponse
import retrofit2.Response
import retrofit2.http.*

interface TheMovieDbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @QueryMap filter: Map<String, String>
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @QueryMap filter: Map<String, String>
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @QueryMap filter: Map<String, String>
    ): Response<MovieDetailResult>

    @GET("search/movie")
    suspend fun getMovieByTitle(
        @QueryMap filter: Map<String, String>
    ): Response<MoviesResponse>
}

