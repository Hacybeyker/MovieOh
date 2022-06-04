package com.hacybeyker.movieoh.domain.entity

import com.hacybeyker.movieoh.R

enum class StreamEntity(
    val icon: Int,
    val type: String,
) {
    NONE(
        icon = R.drawable.icon_netflix,
        type = "none"
    ),
    NETFLIX(
        icon = R.drawable.icon_netflix,
        type = "netflix"
    ),
    AMAZON(
        icon = R.drawable.icon_amazon,
        type = "amazon"
    ),
    HBO(
        icon = R.drawable.icon_hbo,
        type = "hbo"
    )
}

const val NETFLIX = "netflix"
const val AMAZON = "amazon"
const val STAR_PLUS = "starplus"
const val HBO = "hbo"
