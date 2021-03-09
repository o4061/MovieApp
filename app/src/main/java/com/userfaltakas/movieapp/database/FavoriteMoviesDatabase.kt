package com.userfaltakas.movieapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.userfaltakas.movieapp.database.entities.MovieDB

@Database(
    entities = [MovieDB::class],
    version = 3
)

abstract class FavoriteMoviesDatabase : RoomDatabase() {
    abstract fun MovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteMoviesDatabase? = null

        fun getInstance(context: Context): FavoriteMoviesDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteMoviesDatabase::class.java,
                    "movie_database"
                ).fallbackToDestructiveMigration()
                    .build().apply {
                        INSTANCE = this
                    }
            }
        }
    }
}