package de.esd.zeiterfassung.repository

import de.esd.zeiterfassung.EsdBaseException
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.core.Arbeitsverhaeltnis
import de.esd.zeiterfassung.core.Arbeitszeit
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.view.CalendarConfiguration
import de.esd.zeiterfassung.web.TeamUpDateConverter
import de.esd.zeiterfassung.web.TeamupCalendarConfig
import de.esd.zeiterfassung.web.remotemodel.Event
import de.esd.zeiterfassung.web.remotemodel.RemoteArbeitsverhaeltnis
import java.math.BigDecimal

class ZeitArbeitsverhaeltnisMapper {

    private val arbeitsverhaeltnisMapper = ArbeitsverhaeltnisMapper()


    fun mapArbeitsverhaeltnisFromKeys(remoteArbeitsverhaeltnis: RemoteArbeitsverhaeltnis,
                                      config: CalendarConfiguration): Arbeitsverhaeltnis {
        val taetigkeitKey = remoteArbeitsverhaeltnis.taetigkeitKey
        val anbaugeraet = config.anbaugeraete.find { it.key == remoteArbeitsverhaeltnis.anbaugeraetKey }
        val fahrzeug = config.fahrzeuge.find { it.key == remoteArbeitsverhaeltnis.fahrzeugKey }
        val taetigkeit = config.teatigkeiten.find { it.key == taetigkeitKey }
        return ZeitArbeitsverhaeltnis().apply {
            this.taetigkeit = taetigkeit ?: throw EsdBaseException(
                "Ungültiger key: $taetigkeitKey")
            this.anbaugeraet = anbaugeraet
            this.arbeitszeit = remoteArbeitsverhaeltnis.arbeitszeit ?: Arbeitszeit(
                BigDecimal.ZERO)
            this.fahrzeug = fahrzeug
            arbeitsverhaeltnisMapper.mapToArbeitsverhaeltnisFromKeys(this, remoteArbeitsverhaeltnis, config)
        }
    }


    fun fromArbeitsverhaeltnisToRemoteEvent(arbeitsverhaeltnis: ZeitArbeitsverhaeltnis, erstelltVon: String): Event {
        return Event().apply {
            this.title =
                "${arbeitsverhaeltnis.leistungserbringer?.name}_${arbeitsverhaeltnis.leistungsnehmer.name}_${arbeitsverhaeltnis.taetigkeit.bezeichnung}"
            this.subcalendarId = TeamupCalendarConfig.SUBCALENDAR_ID_NACHBARSCHAFTSHILFE
            this.startDt = TeamUpDateConverter.asEuropeBerlinZonedDateTimeString(arbeitsverhaeltnis.datum)
            this.endDt = TeamUpDateConverter.asEuropeBerlinZonedDateTimeString(arbeitsverhaeltnis.calculateEndDatum())
            this.notes = ObjectMapper.objectToJson(fromArbeitsverhaeltnisToRemoteArbeitsverhaeltnis(arbeitsverhaeltnis))
            this.who = erstelltVon
        }
    }

    private fun fromArbeitsverhaeltnisToRemoteArbeitsverhaeltnis(
        arbeitsverhaeltnis: ZeitArbeitsverhaeltnis): RemoteArbeitsverhaeltnis {
        return RemoteArbeitsverhaeltnis().apply {
            this.arbeitszeit = arbeitsverhaeltnis.arbeitszeit
            this.taetigkeitKey = arbeitsverhaeltnis.taetigkeit.key
            this.fahrzeugKey = arbeitsverhaeltnis.fahrzeug?.key
            this.anbaugeraetKey = arbeitsverhaeltnis.anbaugeraet?.key
            arbeitsverhaeltnisMapper.mapToRemoteArbeitsverhaeltnis(this, arbeitsverhaeltnis)
        }
    }

}
