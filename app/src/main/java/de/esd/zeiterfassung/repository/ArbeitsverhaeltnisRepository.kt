package de.esd.zeiterfassung.repository

import de.esd.zeiterfassung.core.Arbeitseinsatz
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.view.CalendarConfiguration
import de.esd.zeiterfassung.view.Suchkriterien
import de.esd.zeiterfassung.web.TeamUpApi
import de.esd.zeiterfassung.web.TeamUpDateConverter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ArbeitsverhaeltnisRepository(private val teamUpApi: TeamUpApi) {

    private val arbeitseinsatzMapper = ArbeitseinsatzMapper()


    fun fetchArbeitsverhaeltnisseFromRemote(suchkriterien: Suchkriterien,
                                            config: CalendarConfiguration): Single<List<Arbeitseinsatz>> {
        val start = TeamUpDateConverter.fromDateToFetchEventsQueryString(suchkriterien.startDate.getSuchkriterium())
        val end = TeamUpDateConverter.fromDateToFetchEventsQueryString(suchkriterien.endDate.getSuchkriterium())
        return teamUpApi.getEvents(start, end)//
            .subscribeOn(Schedulers.io())//
            .map { events ->
                events.events.map {
                    arbeitseinsatzMapper.fromRemoteEventToArbeitseinsatz(it, config)
                }
            }.map { arbeitseinsaetze ->
                arbeitseinsaetze.filter {
                    it.arbeitsverhaeltnis.matchesSuchkriterien(suchkriterien)
                }
            }
    }

    fun addZeitArbeitsverhaeltnisToRemoteCalendar(verhaeltnis: ZeitArbeitsverhaeltnis,
                                                  erstelltVon: Person): Single<Long> {
        val info = EventInfo(erstelltVon = erstelltVon.name)
        val event = arbeitseinsatzMapper.fromZeitArbeitsverhaeltnisToRemoteEvent(verhaeltnis, info)
        return teamUpApi.postEvent(event)//
            .subscribeOn(Schedulers.io()).map { it.event.id.toLong() }
    }

    fun updateZeitArbeitsverhaeltnis(arbeitsverhaeltnis: ZeitArbeitsverhaeltnis, info: EventInfo): Single<String> {
        val remoteEvent = arbeitseinsatzMapper.fromZeitArbeitsverhaeltnisToRemoteEvent(arbeitsverhaeltnis, info)
        return teamUpApi.updateEvent(remoteEvent.id, remoteEvent)//
            .subscribeOn(Schedulers.io())//
            .map {
                it.event.id
            }
    }


    fun addStueckArbeitsverhaeltnisToRemoteCalendar(verhaeltnis: StueckArbeitsverhaeltnis, who: Person): Single<Long> {
        val info = EventInfo(erstelltVon = who.name)
        val event = arbeitseinsatzMapper.fromStueckArbeitsverhaeltnisToRemoteEvent(verhaeltnis, info)
        return teamUpApi.postEvent(event)//
            .subscribeOn(Schedulers.io()).map { it.event.id.toLong() }
    }

    fun updateStueckArbeitsverhaeltnis(arbeitsverhaeltnis: StueckArbeitsverhaeltnis, info: EventInfo): Single<String> {
        val remoteEvent = arbeitseinsatzMapper.fromStueckArbeitsverhaeltnisToRemoteEvent(arbeitsverhaeltnis, info)
        return teamUpApi.updateEvent(remoteEvent.id, remoteEvent)//
            .subscribeOn(Schedulers.io())//
            .map {
                it.event.id
            }
    }


    fun deleteArbeitseinsatz(eventInfo: EventInfo): Single<String> {
        return teamUpApi.deleteEvent(eventInfo.remoteCalenderId, eventInfo.version)//
            .subscribeOn(Schedulers.io())//
            .map { it.undoId }
    }


}
