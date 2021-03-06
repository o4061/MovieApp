package com.userfaltakas.movieapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.userfaltakas.movieapp.communicator.Communicator
import com.userfaltakas.movieapp.constants.Constants
import com.userfaltakas.movieapp.data.enums.Genres
import com.userfaltakas.movieapp.data.movie.MoviesResult
import com.userfaltakas.movieapp.databinding.MovieContainerBinding
import com.userfaltakas.movieapp.repository.Repository
import com.userfaltakas.movieapp.ui.activity.startPage.StartPageViewModel
import com.userfaltakas.movieapp.ui.activity.startPageFactory.StartPageViewModelFactory

class MovieAdapter(
    private val movies: MoviesResult,
    val lambda: (Int) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    lateinit var context: Context
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: StartPageViewModelFactory
    private lateinit var viewModel: StartPageViewModel

    class MyViewHolder(val binding: MovieContainerBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        return MyViewHolder(
            MovieContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movies[position]




        holder.binding.apply {
            this.textViewTitle.text = currentItem.original_title
            this.ratingBar.rating = currentItem.vote_average.toFloat() / 2
            this.textViewReleaseDate.text = dateFormat(currentItem.release_date)
            this.textViewGenre.text = Genres().converter(currentItem.genre_ids[0])
            this.textViewLanguage.text = currentItem.original_language
            Picasso.with(context).load(Constants.image_url + currentItem.poster_path)
                .into(this.imageView)
        }

        holder.binding.root.setOnClickListener {
            lambda(currentItem.id)


        }
    }

    private fun dateFormat(date: String): String {
        return date.take(4)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}