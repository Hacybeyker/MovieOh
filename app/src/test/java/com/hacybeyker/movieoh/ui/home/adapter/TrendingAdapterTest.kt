package com.hacybeyker.movieoh.ui.home.adapter

import android.widget.FrameLayout
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.ui.OnItemMovie
import com.hacybeyker.movieoh.utils.getMovieOne
import com.hacybeyker.movieoh.utils.getMovieTwo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class TrendingAdapterTest {

    private val spyListMovieEntity: ArrayList<MovieEntity> = spy()

    private val mockOnItemMovie: OnItemMovie = mock()
    private lateinit var sutTrendingAdapter: TrendingAdapter

    @Before
    fun setup() {
        sutTrendingAdapter = TrendingAdapter(mockOnItemMovie)
        spyListMovieEntity.add(getMovieOne())
        spyListMovieEntity.add(getMovieTwo())
        sutTrendingAdapter.submitList(spyListMovieEntity)
    }

    @Test
    fun validateAdapter() {
        val viewHolder = sutTrendingAdapter.onCreateViewHolder(
            FrameLayout(RuntimeEnvironment.getApplication()),
            0
        )
        sutTrendingAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(spyListMovieEntity[0])
        whenever(sutTrendingAdapter.itemCount).doReturn(2)

        assertNotNull(sutTrendingAdapter)
        assertEquals(2, sutTrendingAdapter.itemCount)
    }

    @Test
    fun validateObjectAreEquals() {
        spyListMovieEntity.forEachIndexed { index, movieEntity ->
            assertEquals(sutTrendingAdapter.currentList[index], movieEntity)
        }
    }
}
