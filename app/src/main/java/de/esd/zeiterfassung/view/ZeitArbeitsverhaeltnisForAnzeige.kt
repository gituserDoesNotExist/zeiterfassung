package de.esd.zeiterfassung.view

import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.view.ArbeitsverhaeltnisForAnzeige
import de.esd.zeiterfassung.view.BigDecimalConverter
import ru.gildor.databinding.observables.ObservableString

class ZeitArbeitsverhaeltnisForAnzeige : ArbeitsverhaeltnisForAnzeige() {


    var fahrzeug = ObservableString()
    var anbaugeraet = ObservableString()
    var taetigkeit = ObservableString()
    var arbeitszeit = ObservableString()

    fun copyValuesFromArbeitsverhaeltnis(arbeitsverhaeltnis: ZeitArbeitsverhaeltnis) {
        super.copyValuesFromArbeitsverhaeltnis(arbeitsverhaeltnis)
        this.fahrzeug.set(arbeitsverhaeltnis.fahrzeug?.bezeichnung ?: "")
        this.anbaugeraet.set(arbeitsverhaeltnis.anbaugeraet?.bezeichnung ?: "")
        this.taetigkeit.set(arbeitsverhaeltnis.taetigkeit.bezeichnung)
        this.arbeitszeit.set(BigDecimalConverter.bigDecimalToString(arbeitsverhaeltnis.arbeitszeit.dauer))
    }


}