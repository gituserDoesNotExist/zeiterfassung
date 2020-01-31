package de.esd.zeiterfassung.repository

import de.esd.zeiterfassung.EsdBaseException
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.core.*
import de.esd.zeiterfassung.view.CalendarConfiguration
import de.esd.zeiterfassung.web.TeamUpDateConverter
import de.esd.zeiterfassung.web.remotemodel.Event
import de.esd.zeiterfassung.web.remotemodel.RemoteArbeitsverhaeltnis

class ArbeitseinsatzMapper {

    private val zeitArbeitsverhaeltnisMapper = ZeitArbeitsverhaeltnisMapper()
    private val stueckArbeitsverhaeltnisMapper = StueckArbeitsverhaeltnisMapper()

    fun fromRemoteEventToArbeitseinsatz(remoteEvent: Event, config: CalendarConfiguration): Arbeitseinsatz {
        return Arbeitseinsatz().apply {
            this.eventInfo.remoteCalenderId = remoteEvent.id
            this.eventInfo.version = remoteEvent.version
            this.eventInfo.erstelltAm = TeamUpDateConverter.asZonedDateTime(remoteEvent.creationDt)
            this.eventInfo.erstelltVon = remoteEvent.who
            this.arbeitsverhaeltnis = arbeitsverhaeltnisFromJsonString(remoteEvent, config)
        }
    }

    private fun arbeitsverhaeltnisFromJsonString(event: Event, config: CalendarConfiguration): Arbeitsverhaeltnis {
        val json = removeHtmlParagraphs(event.notes)
        val remoteVerhaeltnis = ObjectMapper.fromJson<RemoteArbeitsverhaeltnis>(json)
        if (!remoteVerhaeltnis.isStueckVerhaeltnis() && !remoteVerhaeltnis.isZeitVerhaeltnis()) {
            throw EsdBaseException("Invalid data!")
        }
        return if (remoteVerhaeltnis.isStueckVerhaeltnis()) {
            stueckArbeitsverhaeltnisMapper.mapArbeitsverhaeltnisFromKeys(remoteVerhaeltnis, config)
        } else {
            zeitArbeitsverhaeltnisMapper.mapArbeitsverhaeltnisFromKeys(remoteVerhaeltnis, config)
        }
    }

    private fun removeHtmlParagraphs(notes: String): String {
        return notes.replace("<p>", "").replace("</p>", "")
    }

    /** Sollte beim Updaten verwendet werden */
    fun fromZeitArbeitsverhaeltnisToRemoteEvent(arbeitsverhaeltnis: ZeitArbeitsverhaeltnis, info: EventInfo): Event {
        val who = info.erstelltVon
        return zeitArbeitsverhaeltnisMapper.fromArbeitsverhaeltnisToRemoteEvent(arbeitsverhaeltnis, who).apply {
            this.id = info.remoteCalenderId
            this.version = info.version
        }
    }


    /** Sollte beim Updaten verwendet werden */
    fun fromStueckArbeitsverhaeltnisToRemoteEvent(verhaeltnis: StueckArbeitsverhaeltnis, info: EventInfo): Event {
        val who = info.erstelltVon
        return stueckArbeitsverhaeltnisMapper.fromArbeitsverhaeltnisToRemoteEvent(verhaeltnis, who).apply {
            this.id = info.remoteCalenderId
            this.version = info.version
        }
    }

}