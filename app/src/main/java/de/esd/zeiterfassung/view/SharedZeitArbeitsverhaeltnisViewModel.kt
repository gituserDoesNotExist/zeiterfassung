package de.esd.zeiterfassung.view

import androidx.lifecycle.ViewModel
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis

class SharedZeitArbeitsverhaeltnisViewModel : ViewModel() {

    lateinit var eventInfo: EventInfo
    lateinit var currentArbeitsverhaeltnis: ZeitArbeitsverhaeltnis


}