package com.fieldcode.uitestapplication

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KakaoTests {

    @JvmField
    @Rule
    val testRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    private val kakaoScreen = KakaoScreen()

    @Test
    fun shouldBeAbleToWriteAndClick() {
        kakaoScreen {
            addButton { click() }
            textView {
                isDisplayed()
                hasText("AA")
            }
        }
    }

    fun shouldClickThroughAllTheFragments() {
        kakaoScreen {
            textNextArrow.click()
            animateButton.isDisplayed()
            animationNextArrow.click()
            mainSwitch.isDisplayed()
            switchForwardArrow.click()
            recyclerView.isDisplayed()
            listBackArrow.click()
            mainSwitch.isDisplayed()
            switchBackArrow.click()
            animateButton.isDisabled()
            animationPreviousArrow.click()
            textView.isDisplayed()
        }
    }

    @Test
    fun shouldBeAbleToWriteToField() {
        kakaoScreen {
            editText.typeText("ABC")
            editText.hasText("ABC")
        }
    }

    @Test
    fun testAnimationFragment() {
        launchFragment(R.id.animationFragment)
        kakaoScreen {
            animateButton.click()
            motoImage.isDisplayed()
        }
    }

    @Test
    fun testSwitchesFragment() {
        launchFragment(R.id.switchesFragment)
        kakaoScreen {
            ratingBar.perform { setRatingAt(3.0f) }
            ratingBar.hasRating(3.0f)
        }
    }

    private fun launchFragment(
        destinationId: Int,
        argBundle: Bundle? = null
    ) {
        val launchFragmentIntent = buildLaunchFragmentIntent(destinationId, argBundle)
        testRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent =
        NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().targetContext)
            .setGraph(R.navigation.nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .setArguments(argBundle)
            .createTaskStackBuilder().intents[0]
}