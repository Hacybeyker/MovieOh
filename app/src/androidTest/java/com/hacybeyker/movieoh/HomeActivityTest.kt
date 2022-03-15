package com.hacybeyker.movieoh

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.hacybeyker.movieoh.ui.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun showMainActivityDefault() {
        onView(withId(R.id.tvMessage)).perform(click())
        var message = ""
        activityScenarioRule.scenario.onActivity { activity ->
            message = activity.resources.getString(R.string.hello_world)
        }
        onView(withId(R.id.tvMessage)).check(matches(withText(message)))
    }
}
