package com.hacybeyker.movieoh.ui.home.adapter

import android.widget.FrameLayout
import com.hacybeyker.movieoh.data.model.remote.response.ResultResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toMovieResponseModelList
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.utils.JSONFileLoader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class UpcomingAdapterTest {

    private lateinit var sutUpcomingAdapter: UpcomingAdapter

    private val movieEntityList: List<MovieEntity> by lazy {
        JSONFileLoader()
            .loadJsonString<ResultResponseModel>("response_model_discover.json")
            .toMovieResponseModelList()
    }

    @Before
    fun setup() {
        sutUpcomingAdapter = UpcomingAdapter { }
        sutUpcomingAdapter.submitList(movieEntityList.toMutableList())
    }

    @Test
    fun verifyActionAdapter() {
        // Given
        val viewHolder = sutUpcomingAdapter.onCreateViewHolder(
            parent = FrameLayout(RuntimeEnvironment.getApplication()),
            viewType = 0
        )

        // When
        sutUpcomingAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(movieEntityList[0])

        // Then
        assertNotNull(sutUpcomingAdapter)
        assertNotNull(viewHolder)
        assertEquals(movieEntityList.size, sutUpcomingAdapter.itemCount)
        assertEquals(movieEntityList[0], sutUpcomingAdapter.currentList[0])
    }
}
