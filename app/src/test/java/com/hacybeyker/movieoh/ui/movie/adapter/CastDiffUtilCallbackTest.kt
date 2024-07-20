package com.hacybeyker.movieoh.ui.movie.adapter

import com.hacybeyker.movieoh.data.model.remote.response.CreditsResponseModel
import com.hacybeyker.movieoh.data.model.remote.response.toListCastEntity
import com.hacybeyker.movieoh.domain.entity.CastEntity
import com.hacybeyker.movieoh.utils.JSONFileLoader
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CastDiffUtilCallbackTest {
    private lateinit var sutCastDiffUtilCallback: CastDiffUtilCallback

    private val castEntity: List<CastEntity> by lazy {
        JSONFileLoader()
            .loadJsonString<CreditsResponseModel>("response_model_credits.json")
            .cast?.toListCastEntity() ?: emptyList()
    }

    @Before
    fun setup() {
        sutCastDiffUtilCallback = CastDiffUtilCallback()
    }

    @Test
    fun verifyAreItemsTheSame() {
        // When
        val castOne = castEntity[0]
        val castTwo = castEntity[1]

        // Then
        assertNotNull(sutCastDiffUtilCallback)

        assertTrue(
            sutCastDiffUtilCallback.areItemsTheSame(
                oldItem = castOne,
                newItem = castTwo.copy(id = 74568, originalName = "Chris Hemsworth"),
            ),
        )

        assertFalse(
            sutCastDiffUtilCallback.areItemsTheSame(
                oldItem = castOne,
                newItem = castTwo,
            ),
        )

        assertFalse(
            sutCastDiffUtilCallback.areItemsTheSame(
                oldItem = castOne,
                newItem = castTwo.copy(id = 74568, originalName = ""),
            ),
        )

        assertFalse(
            sutCastDiffUtilCallback.areItemsTheSame(
                oldItem = castOne,
                newItem = castTwo.copy(originalName = "Chris Hemsworth"),
            ),
        )
    }

    @Test
    fun verifyAreContentsTheSame() {
        // When
        val castOne = castEntity[0]
        val castTwo = castEntity[1]

        // Then
        assertTrue(
            sutCastDiffUtilCallback.areItemsTheSame(
                oldItem = castOne,
                newItem = castOne,
            ),
        )

        assertFalse(
            sutCastDiffUtilCallback.areContentsTheSame(
                oldItem = castOne,
                newItem = castTwo,
            ),
        )
    }
}

/*
* Case verifyAreItemsTheSame:
* id igual id true
* id no es igual id false
* originalName  igual originalName true
* originalName no es igual originalName false
*
* true && true      true
* true && false     false
* false && true     false
* false && false    false
*
* ==================================================
*
* Case verifyAreContentsTheSame:
* oldItem igual newItem true
* oldItem no es igual newItem false
* id igual id true
* id no es igual id false
*
* true && true      true
* true && false     false
* false && true     false
* false && false    false
 */
