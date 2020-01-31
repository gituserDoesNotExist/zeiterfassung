package de.esd.zeiterfassung

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import org.threeten.bp.LocalDate

class EsdDatePickerDialog(context: Context, dateSetListener: (LocalDate) -> Unit, preselectedDate: LocalDate) :
    DatePickerDialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert) {

    init {
        val listener = DatePicker.OnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            dateSetListener(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
        }
        datePicker.init(preselectedDate.year, preselectedDate.monthValue - 1, preselectedDate.dayOfMonth, listener)
    }


}