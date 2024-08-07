package com.hacybeyker.movieoh.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hacybeyker.movieoh.databinding.RecyclerMovieBinding
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.extensions.loadImage

class MovieAdapter(
    private val onClick: (MovieEntity) -> Unit,
) : ListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(MovieDiffUtilCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MovieViewHolder {
        return MovieViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<MovieEntity>?) {
        super.submitList(list)
    }

    class MovieViewHolder(
        private val binding: RecyclerMovieBinding,
        private val onClick: (MovieEntity) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(
                parent: ViewGroup,
                onClick: (MovieEntity) -> Unit,
            ): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerMovieBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding, onClick)
            }
        }

        fun bind(item: MovieEntity) {
            with(binding) {
                clMainRecyclerMovie.setOnClickListener { onClick(item) }
                ivMoviePoster.loadImage(item.posterPath)
            }
        }
    }
}
