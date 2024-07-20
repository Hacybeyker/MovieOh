package com.hacybeyker.movieoh.domain.entity

import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.utils.constans.Stream

enum class StreamEntity(
    val icon: Int,
    val type: String,
) {
    NONE(
        icon = R.drawable.icon_netflix,
        type = Stream.NONE,
    ),
    NETFLIX(
        icon = R.drawable.icon_netflix,
        type = Stream.NETFLIX,
    ),
    AMAZON(
        icon = R.drawable.icon_amazon,
        type = Stream.AMAZON,
    ),
    HBO(
        icon = R.drawable.icon_hbo,
        type = Stream.HBO,
    ),
    DISNEY(
        icon = R.drawable.icon_disney,
        type = Stream.DISNEY,
    ),
}
