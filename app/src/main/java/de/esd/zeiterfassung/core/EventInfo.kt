package de.esd.zeiterfassung.core

import de.esd.zeiterfassung.ZoneIds
import org.threeten.bp.ZonedDateTime

data class EventInfo(var remoteCalenderId: String = "", var version: String = "",
                     var erstelltAm: ZonedDateTime = ZonedDateTime.now(ZoneIds.EUROPE_BERLIN.getZone()),
                     var erstelltVon: String = "")
