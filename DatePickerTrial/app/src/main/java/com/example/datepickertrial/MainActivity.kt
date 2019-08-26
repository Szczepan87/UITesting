package com.example.datepickertrial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val datePickerDialog = DatePickerDialog()

        date_button.setOnClickListener {
            datePickerDialog.show(supportFragmentManager, "date picker")
            datePickerDialog.result = { date_textView.text = it}
        }
    }
}
