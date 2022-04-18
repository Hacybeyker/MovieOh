package com.hacybeyker.movieoh.ui.home.adapter

import android.widget.FrameLayout
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.getMovieOne
import com.hacybeyker.movieoh.utils.getMovieTwo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MovieSimilarAdapterTest {

    private val spyListMovieEntity: ArrayList<MovieEntity> = spy()

    private lateinit var sutMovieSimilarAdapter: MovieSimilarAdapter

    @Before
    fun setup() {
        sutMovieSimilarAdapter = MovieSimilarAdapter()
        spyListMovieEntity.add(getMovieOne())
        spyListMovieEntity.add(getMovieTwo())
        sutMovieSimilarAdapter.submitList(spyListMovieEntity)
    }

    @Test
    fun validateAdapter() {
        val viewHolder = sutMovieSimilarAdapter.onCreateViewHolder(
            FrameLayout(RuntimeEnvironment.getApplication()),
            0
        )
        sutMovieSimilarAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(spyListMovieEntity[0])
        whenever(sutMovieSimilarAdapter.itemCount).doReturn(2)

        assertNotNull(sutMovieSimilarAdapter)
        assertEquals(2, sutMovieSimilarAdapter.itemCount)
    }

    @Test
    fun validateObjectAreEquals() {
        spyListMovieEntity.forEachIndexed { index, movieEntity ->
            assertEquals(sutMovieSimilarAdapter.currentList[index], movieEntity)
        }
    }
}
