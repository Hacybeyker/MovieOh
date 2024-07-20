package com.hacybeyker.movieoh.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hacybeyker.movieoh.domain.entity.MovieEntity

class MovieDiffUtilCallback : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(
        oldItem: MovieEntity,
        newItem: MovieEntity,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieEntity,
        newItem: MovieEntity,
    ): Boolean {
        return oldItem == newItem
    }
}
