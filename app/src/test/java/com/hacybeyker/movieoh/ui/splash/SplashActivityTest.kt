package com.hacybeyker.movieoh.ui.splash

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.airbnb.lottie.LottieAnimationView
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.ui.home.HomeActivity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowLooper

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun showSplash() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
        activityScenarioRule.scenario.onActivity { activity ->
            activity.setContentView(shadowOf(activity).contentView)
            assertEquals(R.id.clMainSplash, shadowOf(activity).contentView.id)
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks()
            val tvTitle = activity.findViewById<AppCompatTextView>(R.id.tvTitle)
            val lavMovie = activity.findViewById<LottieAnimationView>(R.id.lavMovie)
            assertEquals(tvTitle.text, activity.resources.getString(R.string.app_name))
            assertThat(lavMovie.visibility, equalTo(View.VISIBLE))
            assertThat(tvTitle.visibility, equalTo(View.VISIBLE))
            val expectedIntent = Intent(activity.applicationContext, HomeActivity::class.java)
            val actualIntent = shadowOf(RuntimeEnvironment.getApplication()).nextStartedActivity
            assertNotNull(expectedIntent)
            assertEquals(expectedIntent.component, actualIntent.component)
        }
    }
}
