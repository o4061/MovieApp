package com.userfaltakas.movieapp.api

import com.userfaltakas.movieapp.constants.Constants.baseUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val API: TheMovieDbApi by lazy {
        retrofit.create(TheMovieDbApi::class.java)
    }
}
