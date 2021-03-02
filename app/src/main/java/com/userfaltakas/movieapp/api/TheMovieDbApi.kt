package com.userfaltakas.movieapp.api

import com.userfaltakas.movieapp.data.popularMovies.PopularMoviesResponse
import com.userfaltakas.movieapp.data.upcomingMovies.UpcomingMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TheMovieDbApi {

    //get popular
    //(https://api.themoviedb.org/3/movie/popular?api_key=77e53ce7603447d4d1b677e478a9b302&language=en-US&page=1)
    //https://api.themoviedb.org/3/movie/upcoming?api_key=77e53ce7603447d4d1b677e478a9b302&language=en-US&page=1

    @GET("popular")
    suspend fun getPopularMovies(
            @QueryMap filter: Map<String, String>): Response<PopularMoviesResponse>

    @GET("upcoming")
    suspend fun getUpcomingMovies(
            @QueryMap filter: Map<String, String>): Response<UpcomingMoviesResponse>

}