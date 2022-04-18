package com.hacybeyker.movieoh.utils

import com.hacybeyker.movieoh.domain.entity.MovieEntity

fun getListMovieEntity(): List<MovieEntity> {
    return arrayListOf(
        getMovieOne(),
        getMovieTwo()
    )
}

fun getMovieOne(): MovieEntity {
    return MovieEntity(
        id = 634649,
        backdropPath = "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
        posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
        originalTitle = "Spider-Man: No Way Home",
        overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
        releaseDate = "2021-12-15",
        title = "Spider-Man: No Way Home",
        voteAverage = 8.5
    )
}

fun getMovieTwo(): MovieEntity {
    return MovieEntity(
        id = 524434,
        backdropPath = "/k2twTjSddgLc1oFFHVibfxp2kQV.jpg",
        posterPath = "/6AdXwFTRTAzggD2QUTt5B7JFGKL.jpg",
        originalTitle = "Eternals",
        overview = "The Eternals are a team of ancient aliens who have been living on Earth in secret for thousands of years. When an unexpected tragedy forces them out of the shadows, they are forced to reunite against mankind’s most ancient enemy, the Deviants.",
        releaseDate = "2021-11-03",
        title = "Eternals",
        voteAverage = 7.3
    )
}
