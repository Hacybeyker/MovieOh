package com.hacybeyker.movieoh.ui.movie.adapter

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
class SimilarAdapterTest {
    private lateinit var sutSimilarAdapter: SimilarAdapter

    private val spyListMovieEntity: ArrayList<MovieEntity> = spy()

    @Before
    fun setup() {
        sutSimilarAdapter = SimilarAdapter { }
        spyListMovieEntity.add(getMovieOne())
        spyListMovieEntity.add(getMovieTwo())
        sutSimilarAdapter.submitList(spyListMovieEntity)
    }

    @Test
    fun validateAdapter() {
        // Given
        whenever(sutSimilarAdapter.itemCount).doReturn(2)
        val viewHolder =
            sutSimilarAdapter.onCreateViewHolder(
                parent = FrameLayout(RuntimeEnvironment.getApplication()),
                viewType = 0,
            )

        // When
        sutSimilarAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(spyListMovieEntity[0])

        // Then
        assertNotNull(sutSimilarAdapter)
        assertNotNull(viewHolder)
        assertEquals(2, sutSimilarAdapter.itemCount)
    }
}
