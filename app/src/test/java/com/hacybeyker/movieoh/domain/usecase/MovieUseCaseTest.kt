package com.hacybeyker.movieoh.domain.usecase

import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.repository.MovieRepository
import com.hacybeyker.movieoh.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MovieUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockMovieRepository: MovieRepository = mock()

    private val mockMovieEntity: MovieEntity = mock()

    private val sutMovieUseCase: MovieUseCase by lazy { MovieUseCase(mockMovieRepository) }

    @Test
    fun `when call fetchTrendingMovie then repository call fetchTrendingMovie`() {
        testCoroutineRule.runBlockingTest {
            whenever(mockMovieRepository.fetchMovie(anyInt())).doReturn(mockMovieEntity)

            val resultData = sutMovieUseCase.fetchMovie(anyInt())

            assertNotNull(sutMovieUseCase)
            assertNotNull(resultData)
            verify(mockMovieRepository, times(numInvocations = 1)).fetchMovie(anyInt())
        }
    }
}
