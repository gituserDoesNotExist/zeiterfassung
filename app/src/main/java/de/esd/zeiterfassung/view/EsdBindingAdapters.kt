package de.esd.zeiterfassung.view

import android.text.InputFilter
import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.esd.zeiterfassung.EsdInputType
import de.esd.zeiterfassung.R

@BindingAdapter("visibleOrGone")
fun View.setVisibleOrGone(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("errorneous")
fun TextInputLayout.seterrorneous(errorneous: Boolean) {
    this.isErrorEnabled = errorneous
    if (errorneous) {
        this.error = this.resources.getString(R.string.wert_eingeben)
    } else {
        this.error = ""
    }
}

@BindingAdapter("inputType")
fun TextInputEditText.setInputType(esdInputType: EsdInputType?) {
    esdInputType?.let {
        this.inputType = it.inputType
        if (it == EsdInputType.NUMBER || it == EsdInputType.DECIMAL) {
            this.filters = arrayOf(InputFilter.LengthFilter(5))
        }
    }
}


