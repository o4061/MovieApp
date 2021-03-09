package com.userfaltakas.movieapp.data.enums

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class MovieTypeRequest : Parcelable {
    Upcoming, Popular, Search, Favorites
}