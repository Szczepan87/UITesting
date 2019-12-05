package com.fieldcode.uitestapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextAndButtonsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply { value = "A" }
    val text: LiveData<String>
        get() = _text

    fun add() {
        "${_text.value}A"
    }

    fun subtract() {
        _text.value?.dropLast(1)
    }
}
