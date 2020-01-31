package de.esd.zeiterfassung.view

import androidx.lifecycle.ViewModel
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis

class SharedStueckArbeitsverhaeltnisViewModel : ViewModel() {

    lateinit var eventInfo: EventInfo
    lateinit var currentArbeitsverhaeltnis: StueckArbeitsverhaeltnis

    lateinit var fullName: String

}