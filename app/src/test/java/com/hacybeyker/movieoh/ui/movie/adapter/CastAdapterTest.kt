package com.hacybeyker.movieoh.ui.movie.adapter

import android.widget.FrameLayout
import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toCreditsEntity
import com.hacybeyker.movieoh.domain.entity.CastEntity
import com.hacybeyker.movieoh.domain.entity.CreditsEntity
import com.hacybeyker.movieoh.utils.JSONFileLoader
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
class CastAdapterTest {

    private lateinit var sutCastAdapter: CastAdapter

    private val spyListCastEntity: ArrayList<CastEntity> = spy()

    private val creditsEntity: CreditsEntity by lazy {
        JSONFileLoader()
            .loadJsonString<CreditsResponseModel>("response_model_credits.json")
            .toCreditsEntity()
    }

    @Before
    fun setup() {
        sutCastAdapter = CastAdapter()
        for (cast in creditsEntity.cast) {
            spyListCastEntity.add(cast)
        }
        sutCastAdapter.submitList(spyListCastEntity)
    }

    @Test
    fun validateAdapter() {
        // Given
        whenever(sutCastAdapter.itemCount).doReturn(creditsEntity.cast.size)
        val viewHolder = sutCastAdapter.onCreateViewHolder(
            parent = FrameLayout(RuntimeEnvironment.getApplication()),
            viewType = 0
        )

        // When
        sutCastAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(spyListCastEntity[0])

        // Then
        assertNotNull(sutCastAdapter)
        assertNotNull(viewHolder)
        assertEquals(creditsEntity.cast.size, sutCastAdapter.itemCount)
    }

    @Test
    fun validateAdapter2() {
        // Given
        whenever(sutCastAdapter.itemCount).doReturn(creditsEntity.cast.size)
        val viewHolder = sutCastAdapter.onCreateViewHolder(
            parent = FrameLayout(RuntimeEnvironment.getApplication()),
            viewType = 0
        )

        // When
        sutCastAdapter.onBindViewHolder(viewHolder, 0)
        viewHolder.bind(spyListCastEntity[0].copy(profilePath = ""))

        // Then
        assertNotNull(sutCastAdapter)
        assertNotNull(viewHolder)
        assertEquals(creditsEntity.cast.size, sutCastAdapter.itemCount)
    }
}
