package de.esd.zeiterfassung.core

import de.esd.zeiterfassung.config.Maschine
import de.esd.zeiterfassung.config.Taetigkeit
import de.esd.zeiterfassung.view.Suchkriterien
import org.threeten.bp.LocalDateTime
import java.math.BigDecimal

class ZeitArbeitsverhaeltnis(var fahrzeug: Maschine? = null, var anbaugeraet: Maschine? = null,
                             var taetigkeit: Taetigkeit = Taetigkeit(),
                             var arbeitszeit: Arbeitszeit = Arbeitszeit(
                                 BigDecimal.ZERO)) : Arbeitsverhaeltnis() {




    override fun calculateEndDatum(): LocalDateTime {
        return datum.atStartOfDay().plusMinutes(arbeitszeit.getTimeInMinutes())
    }

    override fun calculateKostenForArbeitsverhaeltnis(): BigDecimal {
        val dauer = arbeitszeit.dauer
        val kostenFahrzeug = fahrzeug?.stundensatz?.times(dauer) ?: BigDecimal.ZERO
        val kostenAnbaugeraet = anbaugeraet?.stundensatz?.times(dauer) ?: BigDecimal.ZERO
        val kostenLeistungserbringer = leistungserbringer?.stundensatz?.times(dauer) ?: BigDecimal.ZERO

        return kostenFahrzeug.plus(kostenAnbaugeraet).plus(kostenLeistungserbringer)
    }

    override fun matchesSuchkriterien(suchkriterien: Suchkriterien): Boolean {
        val containsTaetigkeit = suchkriterien.shouldSearchForTaetigkeit(taetigkeit.key)
        return super.matchesSuchkriterien(suchkriterien) && containsTaetigkeit
    }

    fun copy(): ZeitArbeitsverhaeltnis {
        val source = this
        return ZeitArbeitsverhaeltnis().apply {
            this.anbaugeraet = source.anbaugeraet
            this.fahrzeug = source.fahrzeug
            this.arbeitszeit = source.arbeitszeit
            this.taetigkeit = source.taetigkeit
            mapToArbeitsverhaeltnis(source, this)
        }
    }


}