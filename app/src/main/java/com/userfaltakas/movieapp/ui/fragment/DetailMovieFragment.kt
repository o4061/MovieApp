package com.userfaltakas.movieapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.userfaltakas.movieapp.constants.Constants
import com.userfaltakas.movieapp.data.movie.MovieDetailResult
import com.userfaltakas.movieapp.databinding.FragmentDetailMovieBinding

class DetailMovieFragment : Fragment() {
    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val bundle = Bundle()
        private val fragment = DetailMovieFragment()

        fun newInstanceMovieDetails(movie: MovieDetailResult, code: Int): DetailMovieFragment {
            bundle.apply {
                putParcelable("MOVIE", movie)
                putInt("CODE", code)
            }

            fragment.arguments = bundle
            return fragment
        }

        fun getArgs(): MovieDetailResult? {
            return fragment.arguments?.getParcelable("MOVIE")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = getArgs()

        movie.let {
            if (it != null) {
                Picasso.with(context).load(Constants.backdrop_url + it.backdrop_path)
                    .into(binding.imageViewBackDrop)
            }
            binding.textViewDetailMovieTitle.text = it?.original_title
            binding.textViewOverView.text = it?.overview
            binding.textViewScore.text = it?.vote_average.toString()
            binding.textViewPopularity.text = it?.popularity?.toInt().toString()
            binding.textViewRuntime.text = runtimeFormat(it?.runtime)
            binding.textViewStatus.text = it?.status
            if (it?.genres?.size!! >= 1) {
                binding.textViewGenre1.text = it.genres[0].name
                binding.textViewGenre1.visibility = View.VISIBLE
            }
            if (it.genres.size >= 2) {
                binding.textViewGenre2.text = it.genres[1].name
                binding.textViewGenre2.visibility = View.VISIBLE
            }
            if (it.genres.size >= 3) {
                binding.textViewGenre3.text = it.genres[2].name
                binding.textViewGenre3.visibility = View.VISIBLE
            }
            if (it.genres.size == 4) {
                binding.textViewGenre4.text = it.genres[3].name
                binding.textViewGenre4.visibility = View.VISIBLE
            }
        }
    }

    private fun runtimeFormat(runtime: Int?): String {
        val hours = runtime?.div(60)
        val minutes = runtime?.rem(60)

        return hours.toString() + "h " + minutes.toString() + "m"
    }
}