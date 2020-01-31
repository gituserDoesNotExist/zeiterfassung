package de.esd.zeiterfassung.view

import androidx.databinding.ObservableInt
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import de.esd.zeiterfassung.view.ArbeitsverhaeltnisForAnzeige
import ru.gildor.databinding.observables.ObservableString

class StueckArbeitsverhaeltnisForAnzeige : ArbeitsverhaeltnisForAnzeige() {


    var stueckzahl = ObservableInt()
    var produktName = ObservableString()

    fun copyValuesFromArbeitsverhaeltnis(arbeitsverhaeltnis: StueckArbeitsverhaeltnis) {
        super.copyValuesFromArbeitsverhaeltnis(arbeitsverhaeltnis)
        this.stueckzahl.set(arbeitsverhaeltnis.stueckzahl)
        this.produktName.set(arbeitsverhaeltnis.produkt.name)
    }


}