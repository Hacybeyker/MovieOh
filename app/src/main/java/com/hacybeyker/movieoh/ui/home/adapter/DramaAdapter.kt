package com.hacybeyker.movieoh.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hacybeyker.movieoh.databinding.RecyclerMovieBinding
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.utils.extensions.loadImage

class DramaAdapter(private val onItemMovie: OnItemMovie) :
    ListAdapter<MovieEntity, DramaAdapter.DramaViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DramaViewHolder {
        return DramaViewHolder.from(parent, onItemMovie)
    }

    override fun onBindViewHolder(holder: DramaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<MovieEntity>?) {
        super.submitList(list)
    }

    class DramaViewHolder(
        private val binding: RecyclerMovieBinding,
        private val onItemMovie: OnItemMovie
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, onItemMovie: OnItemMovie): DramaViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerMovieBinding.inflate(layoutInflater, parent, false)
                return DramaViewHolder(binding, onItemMovie)
            }
        }

        fun bind(item: MovieEntity) {
            with(binding) {
                ivMoviePoster.setOnClickListener { onItemMovie.onClickMovie(item) }
                ivMoviePoster.loadImage(item.posterPath)
            }
        }
    }
}
