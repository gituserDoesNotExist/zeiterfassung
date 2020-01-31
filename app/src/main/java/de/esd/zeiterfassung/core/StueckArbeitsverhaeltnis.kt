package de.esd.zeiterfassung.core

import de.esd.zeiterfassung.config.Produkt
import java.math.BigDecimal

class StueckArbeitsverhaeltnis(var stueckzahl: Int = 0, var produkt: Produkt = Produkt()) : Arbeitsverhaeltnis() {


    override fun calculateKostenForArbeitsverhaeltnis(): BigDecimal {
        return BigDecimal(stueckzahl).times(produkt.kostenProStueck)
    }


    fun copy(): StueckArbeitsverhaeltnis {
        val source = this
        return StueckArbeitsverhaeltnis().apply {
            this.stueckzahl = source.stueckzahl
            this.produkt = source.produkt
            mapToArbeitsverhaeltnis(source, this)
        }
    }

}