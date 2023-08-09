package pe.ralvaro.movietek.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.ralvaro.movietek.databinding.NetworkStateItemBinding

class MovieLoadStateAdapter(
    private val onRetryClick: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.MovieLoadStateViewHolder>() {

    class MovieLoadStateViewHolder(
        private val binding: NetworkStateItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(loadState: LoadState, onRetryClick: () -> Unit) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.tvErrorMsg.isVisible =  !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            binding.tvErrorMsg.text = (loadState as? LoadState.Error)?.error?.message

            binding.btnRetry.setOnClickListener {
                onRetryClick()
            }
        }

    }

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState, onRetryClick)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder(
            NetworkStateItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

}