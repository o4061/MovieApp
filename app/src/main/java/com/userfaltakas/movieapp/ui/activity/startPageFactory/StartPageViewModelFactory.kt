package com.userfaltakas.movieapp.ui.activity.startPageFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel

class StartPageViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartPageViewModel(repository) as T
    }
}