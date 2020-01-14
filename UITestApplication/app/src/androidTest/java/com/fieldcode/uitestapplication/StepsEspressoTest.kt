package com.fieldcode.uitestapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.fieldcode.uitestapplication.RatingValueMatcher.Companion.withRating
import com.fieldcode.uitestapplication.ui.MyAdapter
import org.hamcrest.Description
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StepsEspressoTest {

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private fun launchFragment(
        destinationId: Int,
        argBundle: Bundle? = null
    ) {
        val launchFragmentIntent = buildLaunchFragmentIntent(destinationId, argBundle)
        activityRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent =
        NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .setArguments(argBundle)
            .createTaskStackBuilder().intents[0]

    // custom ViewAction for setting rating od RatingBar
    private fun setRating(rating: Float): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "Set custom rating value"

            override fun getConstraints() = isAssignableFrom(RatingBar::class.java)

            override fun perform(uiController: UiController?, view: View?) {
                (view as RatingBar).rating = rating
            }
        }
    }

    @Before
    fun registerIdlingRes(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun shouldClickThroughAllTheFragments() {
        onView(withId(R.id.text_next_arrow)).check(matches(isDisplayed()))
        // this will fail
        // onView(withId(R.id.text_next_arrow)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.text_next_arrow)).perform(click())
        onView(withId(R.id.animate_button)).check(matches(isDisplayed()))
        onView(withId(R.id.animation_next_arrow)).perform(click())
        onView(withId(R.id.main_switch)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_forward_arrow)).perform(click())
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.list_back_arrow)).perform(click())
        onView(withId(R.id.main_switch)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_back_arrow)).perform(click())
        onView(withId(R.id.animate_button)).check(matches(isDisplayed()))
        onView(withId(R.id.animation_previous_arrow)).perform(click())
        onView(withId(R.id.textView)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldBeAbleToWriteAndClick() {
        onView(withId(R.id.add_button)).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("AA")))
    }

    @Test
    fun shouldBeAbleToWriteToField() {
        onView(withHint("Enter text here"))
            .perform(typeText("ABC"))
            .check(matches(withText("ABC")))
    }

    @Test
    fun testAnimationFragment() {
        // animation testing not supported
        launchFragment(R.id.animationFragment)
        onView(withText("Animate")).perform(click())
        onView(withId(R.id.moto_image)).check(matches(isDisplayed()))
    }

    @Test
    fun testSwitchesFragment() {
        launchFragment(R.id.switchesFragment)
        onView(withId(R.id.main_switch)).check(matches(isNotChecked()))
        onView(withId(R.id.main_switch)).perform(click()).check(matches(isChecked()))
        onView(withId(R.id.option_1)).perform(click()).check(matches(isChecked()))
        // custom ViewAction required to set value
        onView(withId(R.id.ratingBar)).perform(setRating(3.0f)).check(matches(withRating(3.0f)))
    }

    @Test
    fun testRecyclerView() {
        launchFragment(R.id.listFragment)
        val recycler = activityRule.activity.findViewById<RecyclerView>(R.id.recycler_view)
        val items: Int = recycler.adapter?.itemCount ?: 0
        onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MyAdapter.ViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.recycler_view)).perform(scrollToPosition<MyAdapter.ViewHolder>(items - 1))
        onView(withText("Kosecki")).check(matches(isDisplayed()))
    }

    @After
    fun unregisterIdlingRes(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}

class RatingValueMatcher private constructor(private val rating: Float) :
    BoundedMatcher<View?, RatingBar>(RatingBar::class.java) {
    override fun matchesSafely(ratingBar: RatingBar): Boolean {
        return ratingBar.rating == rating
    }

    override fun describeTo(description: Description) {
        description.appendText("with rating value of:")
            .appendValue(rating)
    }

    companion object {
        fun withRating(rating: Float): RatingValueMatcher {
            return RatingValueMatcher(rating)
        }
    }
}