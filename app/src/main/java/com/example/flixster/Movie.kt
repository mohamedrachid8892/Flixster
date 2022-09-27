package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie (
        val movieId: Int,
        private val posterPath: String,
        val title: String,
        val overview: String,
        val backdropPath: String,
        val voteAverage: Double,
) : Parcelable {
        @IgnoredOnParcel
        val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
        @IgnoredOnParcel
        val backdropImageUrl = "https://image.tmdb.org/t/p/w342/$backdropPath"
        companion object {
                fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
                        val movies = mutableListOf<Movie>()
                        for (i in 0 until movieJsonArray.length()){
                                val movieJson = movieJsonArray.getJSONObject(i)
                                movies.add(
                                        Movie(
                                                movieJson.getInt("id"),
                                                movieJson.getString("poster_path"),
                                                movieJson.getString("title"),
                                                movieJson.getString("overview"),
                                                movieJson.getString("backdrop_path"),
                                                movieJson.getDouble("vote_average")
                                        )
                                )
                        }
                        return movies
                }
        }
}