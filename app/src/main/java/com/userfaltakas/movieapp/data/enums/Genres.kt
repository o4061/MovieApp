package com.userfaltakas.movieapp.data.enums

class Genres() {
    fun converter(index: Int): String {
        when (index) {
            28 -> return "Action"
            12 -> return "Adventure"
            16 -> return "Animation"
            35 -> return "Comedy"
            80 -> return "Crime"
            99 -> return "Documentary"
            18 -> return "Drama"
            10751 -> return "Family"
            14 -> return "Fantasy"
            36 -> return "History"
            27 -> return "Horror"
            10402 -> return "Music"
            9648 -> return "Mystery"
            10749 -> return "Romance"
            878 -> return "Fiction"
            10770 -> return "TV_Movie"
            53 -> return "Thriller"
            10752 -> return "War"
            37 -> return "Western"
            else -> return "No Genre"
        }
    }
}
