package com.fieldcode.uitestapplication

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.agoda.kakao.KButton
import com.agoda.kakao.*
import com.agoda.kakao.KImageView
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen

class KakaoScreen : Screen<KakaoScreen>() {
    val addButton = KButton { withId(R.id.add_button) }
    val textView = KTextView { withId(R.id.textView) }
    val textNextArrow = KImageView { withId(R.id.text_next_arrow) }
    val animationNextArrow = KImageView { withId(R.id.animation_next_arrow) }
    val switchForwardArrow = KImageView { withId(R.id.switch_forward_arrow) }
    val listBackArrow = KImageView { withId(R.id.list_back_arrow) }
    val switchBackArrow = KImageView { withId(R.id.switch_back_arrow) }
    val animationPreviousArrow = KImageView { withId(R.id.animation_previous_arrow) }
    val animateButton = KButton { withId(R.id.animate_button) }
    val mainSwitch = KView { withId(R.id.main_switch) }
    val recyclerView = KView { withId(R.id.recycler_view) }
    val editText = KEditText { withId(R.id.edit_text) }
    val motoImage = KImageView { withId(R.id.moto_image) }
    val ratingBar = KRatingBar { withId(R.id.ratingBar) }
}