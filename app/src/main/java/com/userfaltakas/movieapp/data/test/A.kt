package com.userfaltakas.movieapp.data.test

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class A(
    val name: String?,
    val title: List<B>
): Parcelable
