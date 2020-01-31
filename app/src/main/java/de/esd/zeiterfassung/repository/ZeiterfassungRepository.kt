package de.esd.zeiterfassung.repository

import androidx.lifecycle.LiveData
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.core.Arbeitseinsaetze
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.view.CalendarConfiguration
import de.esd.zeiterfassung.view.Suchkriterien
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ZeiterfassungRepository(private val appConfigurationRepository: AppConfigurationRepository,
                              private val arbeitsverhaeltnisRepository: ArbeitsverhaeltnisRepository) {

    fun fetchArbeitseinsaetzeFromRemote(suchkriterien: Suchkriterien): Single<Arbeitseinsaetze> {
        return Single.fromCallable {
            appConfigurationRepository.getConfigurationSynchronous()
        }.subscribeOn(Schedulers.io())//
            .flatMap {
                arbeitsverhaeltnisRepository.fetchArbeitsverhaeltnisseFromRemote(suchkriterien, it)
            }.map {
                Arbeitseinsaetze(it)
            }.doOnSuccess {
                saveTitles(it)
            }
    }

    private fun saveTitles(arbeitseinsaetze: Arbeitseinsaetze) {
        arbeitseinsaetze.einsaetze.forEach {
            appConfigurationRepository.saveTitle(it.arbeitsverhaeltnis.title)
        }
    }

    fun addZeitArbeitsverhaeltnisToRemoteCalendar(arbeitsverhaeltnis: ZeitArbeitsverhaeltnis): Single<Long> {
        return appConfigurationRepository.getAppUser()//
            .flatMap { arbeitsverhaeltnisRepository.addZeitArbeitsverhaeltnisToRemoteCalendar(arbeitsverhaeltnis, it) }
    }

    fun addStueckArbeitsverhaeltnisToRemoteCalendar(arbeitsverhaeltnis: StueckArbeitsverhaeltnis): Single<Long> {
        return appConfigurationRepository.getAppUser()//
            .flatMap {
                arbeitsverhaeltnisRepository.addStueckArbeitsverhaeltnisToRemoteCalendar(arbeitsverhaeltnis, it)
            }
    }

    fun updateZeitArbeitsverhaeltnis(verhaeltnis: ZeitArbeitsverhaeltnis, info: EventInfo): Single<String> {
        return arbeitsverhaeltnisRepository.updateZeitArbeitsverhaeltnis(verhaeltnis, info)
    }

    fun updateStueckArbeitsverhaeltnis(arbeitsverhaeltnis: StueckArbeitsverhaeltnis, info: EventInfo): Single<String> {
        return arbeitsverhaeltnisRepository.updateStueckArbeitsverhaeltnis(arbeitsverhaeltnis, info)
    }


    fun deleteArbeitsverhaeltnis(eventInfo: EventInfo): Single<String> {
        return arbeitsverhaeltnisRepository.deleteArbeitseinsatz(eventInfo)
    }

    fun getConfiguration(): LiveData<CalendarConfiguration> {
        return appConfigurationRepository.getConfigurationAsLiveData()
    }

    fun existsConfiguration(): Single<Boolean> {
        return appConfigurationRepository.existsConfiguration()
    }

    fun downloadRemoteConfiguration(): Single<CalendarConfiguration> {
        return appConfigurationRepository.downloadRemoteConfiguration()
    }

    fun saveAppUser(appUser: Person): Single<Person> {
        return appConfigurationRepository.saveAppUser(appUser)
    }

    fun getTitles(): Single<List<String>> {
        return appConfigurationRepository.getTitles()
    }


}
