package com.hacybeyker.movieoh.ui

import com.hacybeyker.movieoh.domain.entity.MovieEntity

interface OnItemMovie {
    fun onClickMovie(movie: MovieEntity)
}
