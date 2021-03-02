package com.userfaltakas.movieapp.ui.activity.startPage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.userfaltakas.movieapp.data.upcomingMovies.UpcomingMoviesResponse
import com.userfaltakas.movieapp.databinding.StartPageBinding
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPageFactory.StartPageViewModelFactory

class StartPage : AppCompatActivity() {

    private lateinit var binding: StartPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StartPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = StartPageViewModelFactory(repository)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)


    }
}