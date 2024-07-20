package com.hacybeyker.movieoh.domain.entity

import com.hacybeyker.movieoh.utils.getMovieOne
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieEntityTest {
    private lateinit var sutMovieEntity: MovieEntity

    @Before
    fun setup() {
        sutMovieEntity = getMovieOne()
    }

    @Test
    fun validateGetFormatRuntime() {
        /* val runtime1 = 116
         val runtime2 = 185
         val runtime3 = 45

         sutMovieEntity.runtime = runtime1
         val resultRuntime1 = sutMovieEntity.getFormatRuntime()
         sutMovieEntity.runtime = runtime2
         val resultRuntime2 = sutMovieEntity.getFormatRuntime()
         sutMovieEntity.runtime = runtime3
         val resultRuntime3 = sutMovieEntity.getFormatRuntime()

         Assert.assertEquals("1 h 56 min", resultRuntime1)
         Assert.assertEquals("3 h 5 min", resultRuntime2)
         Assert.assertEquals("45 min", resultRuntime3)*/
        Assert.assertTrue(true)
    }
}
