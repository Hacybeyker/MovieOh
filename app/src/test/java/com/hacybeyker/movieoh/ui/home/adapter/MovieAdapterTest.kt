package com.hacybeyker.movieoh.ui.home.adapter

import android.widget.FrameLayout
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.JSONFileLoader
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class MovieAdapterTest {

    private lateinit var sutMovieAdapter: MovieAdapter

    private val movieEntityList: List<MovieEntity> by lazy {
        JSONFileLoader()
            .loadJsonString<ResultResponseModel>("response_model_discover.json")
            .toMovieResponseModelList()
    }

    @Before
    fun setup() {
        sutMovieAdapter = MovieAdapter { }
        sutMovieAdapter.submitList(movieEntityList.toMutableList())
    }

    @Test
    fun `GIVEN view holder WHEN send data THEN verify is ok`() {
        // Given
        val viewHolder = sutMovieAdapter.onCreateViewHolder(
            parent = FrameLayout(RuntimeEnvironment.getApplication()),
            viewType = 0
        )

        // WHEN
        sutMovieAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(movieEntityList[0])

        // THEN
        assertNotNull(sutMovieAdapter)
        assertNotNull(viewHolder)
        assertEquals(movieEntityList.size, sutMovieAdapter.itemCount)
        assertEquals(movieEntityList[0], sutMovieAdapter.currentList[0])
    }
}
