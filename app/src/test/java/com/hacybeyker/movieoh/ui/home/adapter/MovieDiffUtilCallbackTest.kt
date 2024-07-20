package com.hacybeyker.movieoh.ui.home.adapter

import com.hacybeyker.movieoh.utils.getMovieOne
import com.hacybeyker.movieoh.utils.getMovieTwo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieDiffUtilCallbackTest {
    private lateinit var sutMovieDiffUtilCallback: MovieDiffUtilCallback

    @Before
    fun setup() {
        sutMovieDiffUtilCallback = MovieDiffUtilCallback()
    }

    @Test
    fun areItemsTheSame() {
        val movieOld = getMovieOne()
        val movieNew = getMovieTwo()

        val resultTrue =
            sutMovieDiffUtilCallback.areItemsTheSame(oldItem = movieOld, newItem = movieOld)
        val resultFalse =
            sutMovieDiffUtilCallback.areItemsTheSame(oldItem = movieOld, newItem = movieNew)

        assertNotNull(sutMovieDiffUtilCallback)
        assertNotNull(resultTrue)
        assertTrue(resultTrue)
        assertNotNull(resultFalse)
        assertFalse(resultFalse)
    }

    @Test
    fun areContentsTheSame() {
        val movieOld = getMovieOne()
        val movieNew = getMovieTwo()

        val resultTrue =
            sutMovieDiffUtilCallback.areContentsTheSame(oldItem = movieOld, newItem = movieOld)
        val resultFalse =
            sutMovieDiffUtilCallback.areContentsTheSame(oldItem = movieOld, newItem = movieNew)

        assertNotNull(sutMovieDiffUtilCallback)
        assertNotNull(resultTrue)
        assertTrue(resultTrue)
        assertNotNull(resultFalse)
        assertFalse(resultFalse)
    }
}
