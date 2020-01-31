package de.esd.zeiterfassung.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.config.RemoteCalendarMetadata
import de.esd.zeiterfassung.persistence.CalendarConfigurationDao
import de.esd.zeiterfassung.persistence.CalendarConfigurationEntity
import de.esd.zeiterfassung.persistence.TitleDao
import de.esd.zeiterfassung.persistence.TitleEntity
import de.esd.zeiterfassung.view.CalendarConfiguration
import de.esd.zeiterfassung.web.TeamUpApi
import de.esd.zeiterfassung.web.remotemodel.ConfigurationWrapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class AppConfigurationRepository(private val calendarConfigurationDao: CalendarConfigurationDao,
                                 private val titleDao: TitleDao, private val teamUpApi: TeamUpApi) {

    private val calendarConfigMapper = CalendarConfigurationMapper()

    fun existsConfiguration(): Single<Boolean> {
        return Single.fromCallable<Boolean> { calendarConfigurationDao.existsConfiguration() }
            .subscribeOn(Schedulers.io())
    }

    fun getConfigurationAsLiveData(): LiveData<CalendarConfiguration> {
        return Transformations.map(calendarConfigurationDao.getConfigurationAsLiveData()) {
            calendarConfigMapper.fromConfigEntityToCalendarConfig(it)
        }
    }

    fun getConfigurationSynchronous(): CalendarConfiguration {
        return calendarConfigurationDao.getConfiguration().subscribeOn(Schedulers.io())//
            .map {
                calendarConfigMapper.fromConfigEntityToCalendarConfig(it)
            }.blockingGet()
    }

    fun getAppUser(): Single<Person> {
        return calendarConfigurationDao.getConfiguration().subscribeOn(Schedulers.io())//
            .map { it.appUser }
    }

    fun downloadRemoteConfiguration(): Single<CalendarConfiguration> {
        return teamUpApi.getConfiguration()//
            .subscribeOn(Schedulers.io())//
            .map {
                calendarConfigMapper.fromRemoteMetadataToCalendarConfigurationEntity(extractKalenderMetadata(it))
            }.map {
                upsertConfiguration(it)
            }
    }

    private fun upsertConfiguration(config: CalendarConfigurationEntity): CalendarConfiguration {
        if (calendarConfigurationDao.existsConfiguration()) {
            config.appUser = getConfigurationSynchronous().appUser
            calendarConfigurationDao.updateConfiguration(config)
        } else {
            calendarConfigurationDao.insertConfiguration(config)
        }
        return getConfigurationSynchronous()
    }

    private fun extractKalenderMetadata(it: ConfigurationWrapper): RemoteCalendarMetadata {
        val metadataString = it.configuration?.generalSettings?.about?.replace("<p>", "")?.replace("</p>", "")

        return Gson().fromJson(metadataString, RemoteCalendarMetadata::class.java)
    }

    fun saveAppUser(appUser: Person): Single<Person> {
        return calendarConfigurationDao.getConfiguration().subscribeOn(Schedulers.io())//
            .map {
                it.apply { this.appUser = appUser }
            }.map {
                calendarConfigurationDao.updateConfiguration(it)
                it.appUser
            }
    }

    fun saveTitle(title: String): Long {
        return titleDao.insertTitle(TitleEntity(title))
    }


    fun getTitles(): Single<List<String>> {
        return titleDao.getTitles().subscribeOn(Schedulers.io())//
            .map { titles ->
                titles.map { it.title }
            }
    }


}