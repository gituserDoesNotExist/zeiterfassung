package de.esd.zeiterfassung

import android.content.Context
import de.esd.zeiterfassung.R

object KeineAuswahl {

    lateinit var value : String

    fun init(context: Context) {
        value = context.resources.getString(R.string.keine_auswahl)
    }

}