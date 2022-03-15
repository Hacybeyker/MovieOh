package com.hacybeyker.movieoh.ui.splash

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.hacybeyker.movieoh.R
import com.hacybeyker.movieoh.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun showSplashActivity() {
        onView(withId(R.id.tvTitle)).perform(click())
        var message = ""
        activityScenarioRule.scenario.onActivity { activity ->
            message = activity.resources.getString(R.string.app_name)
        }
        onView(withId(R.id.tvTitle)).check(matches(withText(message)))
    }

    @Test
    fun splashActivityGoToHomeActivity() {
        activityScenarioRule.scenario.onActivity { activity ->
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

}