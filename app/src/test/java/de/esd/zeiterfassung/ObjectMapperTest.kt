package de.esd.zeiterfassung

import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.config.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.esd.zeiterfassung.core.Arbeitseinsatz
import de.esd.zeiterfassung.core.Arbeitszeit
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import org.junit.Test
import org.threeten.bp.LocalDate
import java.math.BigDecimal

class ObjectMapperTest {

    @Test
    fun jsonToObjects() {
        val zeitArbeitsverhaeltnis = ZeitArbeitsverhaeltnis().apply {
            this.anbaugeraet = Anbaugeraet()
            this.arbeitszeit = Arbeitszeit("5.0")
            this.fahrzeug = Fahrzeug()
            this.taetigkeit = Taetigkeit()
            this.kommentar = "sfdf"
            this.leistungsnehmer = Person()
            this.leistungserbringer = Person()
            this.datum = LocalDate.now()
        }
        val stueckArbeitsverhaeltnis = StueckArbeitsverhaeltnis().apply {
            this.kommentar = "sfdf"
            this.leistungsnehmer = Person()
            this.leistungserbringer = Person()
            this.datum = LocalDate.now()
            this.stueckzahl = 2
            this.produkt = Produkt("KEY", "sdfsf", BigDecimal.TEN)
        }
        val zeitArbeitsverhaeltnisJson = ObjectMapper.objectToJson(zeitArbeitsverhaeltnis)
        val stueckArbeitsverhaeltnisJson = ObjectMapper.objectToJson(stueckArbeitsverhaeltnis)

        var arbeitseinsatz = ObjectMapper.fromJson<Arbeitseinsatz>(zeitArbeitsverhaeltnisJson)
        println(arbeitseinsatz)

        throw EsdBaseException("sfd")
        arbeitseinsatz = ObjectMapper.fromJson<Arbeitseinsatz>(stueckArbeitsverhaeltnisJson)
        println(arbeitseinsatz)


    }

    companion object {
        inline fun <reified T> fromJson(json: String): T {
            return Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
    }
}