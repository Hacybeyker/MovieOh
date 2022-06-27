package com.hacybeyker.movieoh.data.model.remote.response

import com.hacybeyker.movieoh.utils.getMovieResponseModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieResponseModelTest {

    private lateinit var sutMovieResponseModel: MovieResponseModel

    @Test
    fun validateStreamFalse() {
        val homePage = "https://moonfall.movie"
        sutMovieResponseModel = getMovieResponseModel().copy(homepage = homePage)

        val result = sutMovieResponseModel.isAvailableStream()

        assertFalse(result)
    }

    @Test
    fun validateStreamTrue() {
        val homePageNetflix = "https://netflix.movie"
        sutMovieResponseModel = getMovieResponseModel().copy(homepage = homePageNetflix)

        val resultNetflix = sutMovieResponseModel.isAvailableStream()

        assertTrue(resultNetflix)

        val homePageAmazon = "https://amazon.movie"
        sutMovieResponseModel = getMovieResponseModel().copy(homepage = homePageAmazon)

        val resultAmazon = sutMovieResponseModel.isAvailableStream()

        assertTrue(resultAmazon)

        val homePageHbo = "https://hbo.movie"
        sutMovieResponseModel = getMovieResponseModel().copy(homepage = homePageHbo)

        val resultHbo = sutMovieResponseModel.isAvailableStream()

        assertTrue(resultHbo)
    }
}
