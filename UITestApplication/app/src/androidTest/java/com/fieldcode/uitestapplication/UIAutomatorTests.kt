package com.fieldcode.uitestapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class UIAutomatorTests {

    companion object {
        const val BASIC_PACKAGE_NAME = "com.fieldcode.uitestapplication"
        const val LAUNCH_TIMEOUT: Long = 5000
        const val STANDARD_TIMEOUT: Long = 500
    }

    private lateinit var device: UiDevice

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun startMainActivityFromHomeScreen() {
        // init device
        device = UiDevice.getInstance(getInstrumentation())

        // go to home screen
        device.pressHome()

        // wait for launcher
        val launcherPackage: String = getLauncherPackageName()
        assertThat(launcherPackage, notNullValue())

        device.wait(Until.hasObject(By.pkg(BASIC_PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT)

        val context = getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(BASIC_PACKAGE_NAME)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(BASIC_PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT)
    }

    @Test
    fun checkPreconditions() {
        assertThat(device, notNullValue())
    }

    @Test
    fun testTexts() {
        device.findObject(By.res(BASIC_PACKAGE_NAME, "edit_text")).text = "ABC"
        device.findObject(By.res(BASIC_PACKAGE_NAME, "add_button")).click()

        val changedText = device.wait(
            Until.findObject((By.res(BASIC_PACKAGE_NAME, "textView"))),
            STANDARD_TIMEOUT
        )
        val changedEditText =
            device.wait(Until.findObject(By.res(BASIC_PACKAGE_NAME, "edit_text")), STANDARD_TIMEOUT)

        device.setOrientationRight()

        // make assertions after timeout
        assertThat(changedText.text, `is`(equalTo("AA")))
        assertThat(changedEditText.text, `is`(equalTo("ABC")))
    }

    @Test
    fun checkSwitches() {
        launchFragment(R.id.switchesFragment)

        val mainSwitch = device.findObject(By.res(BASIC_PACKAGE_NAME, "main_switch"))
        mainSwitch.click()

        with(device) {
            pressHome()
            pressRecentApps()
            findObject(
                UiSelector().text(
                    getTargetContext().getString(
                        getTargetContext().applicationInfo.labelRes
                    )
                )
            ).clickAndWaitForNewWindow()
        }

        assertThat(mainSwitch.isChecked, `is`(true))
    }

    @Test
    fun checkAnimations() {
        launchFragment(R.id.animationFragment)

        val button = device.findObject(By.res(BASIC_PACKAGE_NAME, "animate_button"))

        button.click()

        onView(withId(R.id.moto_image)).check(matches(isDisplayed()))
    }

    private fun launchFragment(
        destinationId: Int,
        argBundle: Bundle? = null
    ) {
        val launchFragmentIntent = buildLaunchFragmentIntent(destinationId, argBundle)
        activityRule.launchActivity(launchFragmentIntent)
    }

    private fun buildLaunchFragmentIntent(destinationId: Int, argBundle: Bundle?): Intent =
        NavDeepLinkBuilder(getInstrumentation().targetContext)
            .setGraph(R.navigation.nav_graph)
            .setComponentName(MainActivity::class.java)
            .setDestination(destinationId)
            .setArguments(argBundle)
            .createTaskStackBuilder().intents[0]

    private fun getLauncherPackageName(): String {
        // Create launcher Intent
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        // Use PackageManager to get the launcher package name
        val pm = getApplicationContext<Context>().packageManager
        val resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.activityInfo.packageName
    }
}