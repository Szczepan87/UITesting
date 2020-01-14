package com.fieldcode.uitestapplication.ext

import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.AnimRes
import com.fieldcode.uitestapplication.EspressoIdlingResource

fun ImageView.animateWithEspresso(context: Context?, @AnimRes resId: Int) {
    EspressoIdlingResource.increment()
    startAnimation(AnimationUtils.loadAnimation(context, resId))
    Thread.sleep(1000)
    EspressoIdlingResource.decrement()
}