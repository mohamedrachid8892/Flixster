package com.example.flixster

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>, val orientation: Int)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")

        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder position $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvOverview = itemView.findViewById<TextView>(R.id.tvOverview)
        private val ivPoster = itemView.findViewById<ImageView>(R.id.ivPoster)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            val movieRating = movie.voteAverage
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            val radius = 30
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context)
                    .load(movie.backdropImageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.image_not_found)
                    .into(ivPoster)
            }
            else {
                Glide.with(context)
                    .load(movie.posterImageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.image_not_found)
                    .into(ivPoster)
            }
            if (movieRating >= 7.5) {
                val playButton = itemView.findViewById<ImageView>(R.id.playButton)
                playButton.visibility = View.VISIBLE
            }
        }

        override fun onClick(v: View?) {
            val movie = movies[adapterPosition]

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
    }
}
