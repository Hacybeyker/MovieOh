package com.hacybeyker.movieoh.ui.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hacybeyker.movieoh.domain.entity.CastEntity

class CastDiffUtilCallback : DiffUtil.ItemCallback<CastEntity>() {

    override fun areItemsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem.id == newItem.id && oldItem.originalName == newItem.originalName
    }

    override fun areContentsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
        return oldItem == newItem && oldItem.id == newItem.id
    }
}

class CastDiffUtilCallback2(
    private val oldListCast: List<CastEntity>,
    private val newListCast: List<CastEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return this.oldListCast.size
    }

    override fun getNewListSize(): Int {
        return this.newListCast.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListCast[oldItemPosition].originalName == newListCast[newItemPosition].originalName
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListCast[oldItemPosition].originalName == newListCast[newItemPosition].originalName
    }
}
