package de.esd.zeiterfassung

import android.text.InputType

enum class EsdInputType(val inputType: Int) {


    TEXT(InputType.TYPE_CLASS_TEXT), NUMBER(InputType.TYPE_CLASS_NUMBER), DECIMAL(0x00002002);

}