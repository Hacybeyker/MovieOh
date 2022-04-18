package com.hacybeyker.movieoh.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(this.context)
            .asBitmap()
            .load("https://image.tmdb.org/t/p/w1280$url")
            .into(this)
    }
}
