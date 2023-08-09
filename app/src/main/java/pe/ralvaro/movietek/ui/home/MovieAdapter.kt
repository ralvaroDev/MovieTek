package pe.ralvaro.movietek.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import pe.ralvaro.movietek.R
import pe.ralvaro.movietek.data.models.Movie
import pe.ralvaro.movietek.databinding.ItemMovieBinding

class MovieAdapterPager(
    private val onItemClick: (Int) -> Unit
) : PagingDataAdapter<Movie, MovieAdapterPager.MovieViewHolder>(DiffCallback) {

    class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(current: Movie, onItemClick: (Int) -> Unit) {
            binding.ivPoster.load(current.posterPath) {
                error(R.drawable.img_not_found)
                fallback(R.drawable.img_not_found)
            }
            binding.tvMovieTitle.text = current.title
            binding.tvMovieTitle.setOnClickListener {
                onItemClick(current.id)
            }
        }

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val current = getItem(position)
        current?.let {
            holder.bind(it, onItemClick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}