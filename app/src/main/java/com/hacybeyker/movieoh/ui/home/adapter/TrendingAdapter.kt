package com.hacybeyker.movieoh.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hacybeyker.movieoh.databinding.RecyclerMovieBinding
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.extensions.loadImage

class TrendingAdapter :
    ListAdapter<MovieEntity, TrendingAdapter.TrendingViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        return TrendingViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<MovieEntity>?) {
        super.submitList(list)
    }

    class TrendingViewHolder(private val binding: RecyclerMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): TrendingViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerMovieBinding.inflate(layoutInflater, parent, false)
                return TrendingViewHolder(binding)
            }
        }

        fun bind(item: MovieEntity) {
            with(binding) {
                ivMoviePoster.loadImage(item.posterPath)
            }
        }
    }
}
