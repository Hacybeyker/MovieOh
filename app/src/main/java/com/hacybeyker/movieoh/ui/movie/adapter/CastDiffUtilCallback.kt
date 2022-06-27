package com.hacybeyker.movieoh.ui.movie.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hacybeyker.movieoh.domain.entity.CastEntity

class CastDiffUtilCallback : DiffUtil.ItemCallback<CastEntity>() {

    override fun areItemsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.originalName == newItem.originalName
    }

    override fun areContentsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem == newItem
    }
}
