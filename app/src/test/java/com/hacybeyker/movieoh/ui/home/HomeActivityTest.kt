/*
package com.hacybeyker.movieoh.ui.home

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.domain.entity.MovieEntity
import com.hacybeyker.movieoh.domain.usecase.TrendingUseCase
import com.hacybeyker.movieoh.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.kotlin.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sutHomeActivity: HomeActivity
    */
/*private val sutHomeActivity: HomeActivity = mock()*//*


    */
/*private val mockHomeViewModel: HomeViewModel = mock()*//*


    private lateinit var homeViewModel: HomeViewModel

    private val mockUseCase: TrendingUseCase = mock()

    private val mockDispatcher: CoroutineDispatcher = mock()

    private val mockTrendingLiveData: LiveData<List<MovieEntity>> = mock()

    private val trendingCaptor = argumentCaptor<Observer<List<MovieEntity>>>()

    @Before
    fun setup() {
        sutHomeActivity = spy(HomeActivity())
        homeViewModel = spy(HomeViewModel(mockUseCase, mockDispatcher))
    }

    @Test(expected = NullPointerException::class)
    fun homeObserveTrending() {

        */
/* runBlocking {
             whenever(mockHomeViewModel.trendingMovieLiveData).doReturn(mockTrendingLiveData)

             println("Here - Test1")
             var activity: HomeActivity
             println("Here - Test2")
             val activityController: ActivityController<HomeActivity> =
                 Robolectric.buildActivity(HomeActivity::class.java)
             println("Here - Test3")
             activity = activityController.get()
             println("Here - Test4")
             activityController.create()
             println("Here - Test5")
             activity.setTestViewModel(mockHomeViewModel)
             println("Here - Test6")
             activityController.start()
             println("Here - Test7")
             verify(mockTrendingLiveData).observe(
                 any(LifecycleOwner::class.java),
                 trendingCaptor.capture()
             )
             println("Here - Test8")
         }*//*



        */
/*=====================================================================================*//*


        runBlocking {
            var activity: HomeActivity
            println("Here - Test2")
            val activityController: ActivityController<HomeActivity> =
                Robolectric.buildActivity(HomeActivity::class.java)
            println("Here - Test3")
            activity = activityController.get()
            println("Here - Test4")
            //sutHomeActivity.setContentView(Shadows.shadowOf(activity).contentView)
            println("Here - Test5")
            //whenever(sutHomeActivity.viewModel).doReturn(homeViewModel)
            whenever(homeViewModel.trendingMovieLiveData).doReturn(mockTrendingLiveData)
            whenever(mockUseCase.fetchTrendingMovie(anyInt())).doReturn(arrayListOf())
            */
/*whenever(homeViewModel.trendingMovieLiveData).doReturn(mockTrendingLiveData)*//*

            //activity.viewModel = mockHomeViewModel
            activityController.create()
            println("Here - Test6")
            activity.setTestViewModel(homeViewModel)
            println("Here - Test7")
            activityController.start()
            */
/*val rvMovieTopRated = activity.findViewById<RecyclerView>(R.id.rvMovieTopRated)
            println("Here - ${rvMovieTopRated.visibility}")
            println("Here - Test6")
            activity.setTestViewModel(homeViewModel)
            println("Here - Test7")
            activityController.start()
            println("Here - Test8")*//*

            println("Here - Test8")
            Assert.assertTrue(true)
            println("Here - Test9")
        }

        */
/* verify(mockTrendingLiveData).observe(
             any(LifecycleOwner::class.java),
             trendingCaptor.capture()
         )*//*



        */
/*onView(withId(R.id.rvMovieTopRated)).check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))*//*

        */
/*onView(withId(R.id.rvMovieTopRated)).perform(ViewActions.click())
        Assert.assertEquals(View.VISIBLE, rvMovieTopRated.visibility)*//*



        */
/*=====================================================================================*//*

        */
/*activityScenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
        activityScenarioRule.scenario.onActivity { activity ->

            whenever(mockHomeViewModel.trendingMovieLiveData).doReturn(mockTrendingLiveData)
            //activity.setTestViewModel(mockHomeViewModel)
            println("Here - Test1")
            activity.setContentView(Shadows.shadowOf(activity).contentView)
            println("Here - Test2")
            val rvMovieTopRated = activity.findViewById<RecyclerView>(R.id.rvMovieTopRated)
            println("Here - Test3")
            *//*
*/
/*assertEquals(rvMovieTopRated.visibility, View.GONE)*//*
*/
/*

            activityScenarioRule.scenario.moveToState(Lifecycle.State.STARTED)
            assertEquals(R.id.clMainHome, Shadows.shadowOf(activity).contentView.id)
            println("Here - Test3.1: ${rvMovieTopRated.visibility}")
            assertEquals(rvMovieTopRated.visibility, View.GONE)

            println("Here - Test4")
            verify(mockTrendingLiveData).observe(
                any(LifecycleOwner::class.java),
                trendingCaptor.capture()
            )

            assertEquals(rvMovieTopRated.visibility, View.VISIBLE)


            *//*
*/
/*whenever(mockHomeViewModel.trendingMovieLiveData).doReturn(mockTrendingLiveData)
            activity.setTestViewModel(mockHomeViewModel)
            activityScenarioRule.scenario.moveToState(Lifecycle.State.STARTED)

            verify(mockTrendingLiveData).observe(
                any(LifecycleOwner::class.java),
                trendingCaptor.capture()
            )

            assertEquals(rvMovieTopRated.visibility, View.VISIBLE)*//*
*/
/*
        }*//*



        */
/*=====================================================================================*//*



        */
/* runBlocking {
             println("Here - Test1")
             onView(withId(R.id.rvMovieTopRated)).check(matches(isDisplayed()))
             println("Here - Test2")
         }*//*


        */
/*whenever(mockHomeViewModel.trendingMovieLiveData).doReturn(mockTrendingLiveData)
        activity.setTestViewModel(mockHomeViewModel)
        activityScenarioRule.scenario.moveToState(Lifecycle.State.STARTED)

        verify(mockTrendingLiveData).observe(
            any(LifecycleOwner::class.java),
            trendingCaptor.capture()
        )

        println("Here - Test1")
        onView(withId(R.id.rvMovieTopRated)).check(matches(withEffectiveVisibility(VISIBLE)))*//*

    }
}*/
