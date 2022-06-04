package com.hacybeyker.movieoh.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hacybeyker.movieoh.databinding.RecyclerMovieBinding
import com.hacybeyker.movieoh.domain.entity.CastEntity
import com.hacybeyker.movieoh.utils.extensions.loadImage

class CastAdapter : ListAdapter<CastEntity, CastAdapter.CastViewHolder>(CastDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<CastEntity>?) {
        super.submitList(list)
    }

    class CastViewHolder(
        private val binding: RecyclerMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CastViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerMovieBinding.inflate(layoutInflater, parent, false)
                return CastViewHolder(binding)
            }
        }

        fun bind(item: CastEntity) {
            with(binding) {
                if (item.profilePath != "") {
                    ivMoviePoster.loadImage(item.profilePath)
                }
            }
        }
    }
}
