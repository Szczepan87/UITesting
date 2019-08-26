package com.example.datepickertrial

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class DatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    var result : ((String) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d("DATE: ", "$dayOfMonth - $month - $year")
        val timePickerDialog = TimePickerDialog()
        timePickerDialog.show(fragmentManager, "time picker")
        timePickerDialog.result = { it -> this.result?.invoke("DATE: $dayOfMonth-${month+1}-$year $it") }
    }
}