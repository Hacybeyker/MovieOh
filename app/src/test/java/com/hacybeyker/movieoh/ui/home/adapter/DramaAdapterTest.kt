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
class DramaAdapterTest {

    private lateinit var sutDramaAdapter: DramaAdapter

    private val movieEntityList: List<MovieEntity> by lazy {
        JSONFileLoader()
            .loadJsonString<ResultResponseModel>("response_model_discover.json")
            .toMovieResponseModelList()
    }

    @Before
    fun setup() {
        sutDramaAdapter = DramaAdapter { }
        sutDramaAdapter.submitList(movieEntityList.toMutableList())
    }

    @Test
    fun verifyActionAdapter() {
        // Given
        val viewHolder = sutDramaAdapter.onCreateViewHolder(
            parent = FrameLayout(RuntimeEnvironment.getApplication()),
            viewType = 0
        )

        // When
        sutDramaAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(movieEntityList[0])

        // Then
        assertNotNull(sutDramaAdapter)
        assertNotNull(viewHolder)
        assertEquals(movieEntityList.size, sutDramaAdapter.itemCount)
        assertEquals(movieEntityList[0], sutDramaAdapter.currentList[0])
    }
}
