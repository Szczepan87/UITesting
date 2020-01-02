package com.fieldcode.uitestapplication

import android.content.Intent
import android.content.pm.PackageManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class UIAutomatorTests {

    companion object {
        const val BASIC_PACKAGE_NAME = "com.fieldcode.uitestapplication.ui"
        const val LAUNCH_TIMEOUT: Long = 5000
    }

    private lateinit var device: UiDevice

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
    }

    @Test
    fun checkPreconditions(){
        assertThat(device, notNullValue())
    }

    private fun getLauncherPackageName(): String {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        val packageManager: PackageManager = getApplicationContext<MainActivity>().packageManager
        val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return resolveInfo.resolvePackageName
    }
}