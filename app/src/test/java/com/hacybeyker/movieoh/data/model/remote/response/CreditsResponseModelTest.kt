package com.hacybeyker.movieoh.data.model.remote.response

import com.hacybeyker.movieoh.utils.JSONFileLoader
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CreditsResponseModelTest {
    private lateinit var sutCreditsResponseModel: CreditsResponseModel

    @Before
    fun setup() {
        val creditsResponseModel = JSONFileLoader().loadJsonString<CreditsResponseModel>("response_model_credits.json")
        sutCreditsResponseModel = creditsResponseModel
    }

    @Test
    fun `mapper toCreditsEntity is success`() {
        val resultData = sutCreditsResponseModel.toCreditsEntity()
        assertNotNull(resultData)
    }
}
