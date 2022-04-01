package com.hacybeyker.movieoh.utils

import com.hacybeyker.movieoh.domain.entity.MovieEntity

private fun getListMovieEntity(): List<MovieEntity> {
    return arrayListOf(
        MovieEntity(
            id = 1,
            backdropPath = "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
            posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            originalTitle = "Spider-Man: No Way Home",
            overview = "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            releaseDate = "2021-12-15",
            title = "Spider-Man: No Way Home",
            voteAverage = 8.5
        )
    )
}
