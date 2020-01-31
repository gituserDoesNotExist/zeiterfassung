package de.esd.zeiterfassung.repository

import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.core.Arbeitsverhaeltnis
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import de.esd.zeiterfassung.config.Produkt
import de.esd.zeiterfassung.view.CalendarConfiguration
import de.esd.zeiterfassung.web.TeamUpDateConverter
import de.esd.zeiterfassung.web.TeamupCalendarConfig
import de.esd.zeiterfassung.web.remotemodel.Event
import de.esd.zeiterfassung.web.remotemodel.RemoteArbeitsverhaeltnis
import java.math.BigInteger

class StueckArbeitsverhaeltnisMapper {

    private val arbeitsverhaeltnisMapper = ArbeitsverhaeltnisMapper()

    fun mapArbeitsverhaeltnisFromKeys(remoteArbeitsverhaeltnis: RemoteArbeitsverhaeltnis,
                                      config: CalendarConfiguration): Arbeitsverhaeltnis {
        val produktKey = remoteArbeitsverhaeltnis.produktKey
        val produkt = config.produkte.find { it.key == produktKey }

        return StueckArbeitsverhaeltnis().apply {
            this.produkt = produkt ?: Produkt()
            this.stueckzahl = remoteArbeitsverhaeltnis.stueckzahl?.toInt() ?: 0
            arbeitsverhaeltnisMapper.mapToArbeitsverhaeltnisFromKeys(this, remoteArbeitsverhaeltnis, config)
        }

    }


    /** Sollte beim Anlegen verwendet werden */
    fun fromArbeitsverhaeltnisToRemoteEvent(arbeitsverhaeltnis: StueckArbeitsverhaeltnis, who: String): Event {
        return Event().apply {
            this.title =
                "${arbeitsverhaeltnis.leistungserbringer?.name}_${arbeitsverhaeltnis.leistungsnehmer.name}_${arbeitsverhaeltnis.produkt.name}"
            this.subcalendarId = TeamupCalendarConfig.SUBCALENDAR_ID_NACHBARSCHAFTSHILFE
            this.startDt = TeamUpDateConverter.asEuropeBerlinZonedDateTimeString(arbeitsverhaeltnis.datum)
            this.endDt = TeamUpDateConverter.asEuropeBerlinZonedDateTimeString(arbeitsverhaeltnis.calculateEndDatum())
            this.notes = ObjectMapper.objectToJson(fromArbeitsverhaeltnisToRemoteArbeitsverhaeltnis(arbeitsverhaeltnis))
            this.who = who
        }
    }

    private fun fromArbeitsverhaeltnisToRemoteArbeitsverhaeltnis(
        arbeitsverhaeltnis: StueckArbeitsverhaeltnis): RemoteArbeitsverhaeltnis {
        return RemoteArbeitsverhaeltnis().apply {
            this.stueckzahl = BigInteger.valueOf(arbeitsverhaeltnis.stueckzahl.toLong())
            this.produktKey = arbeitsverhaeltnis.produkt.key
            arbeitsverhaeltnisMapper.mapToRemoteArbeitsverhaeltnis(this, arbeitsverhaeltnis)
        }
    }


}
