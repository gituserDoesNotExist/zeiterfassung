package de.esd.zeiterfassung.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.esd.zeiterfassung.persistence.AppDatabase
import de.esd.zeiterfassung.web.TeamupServiceGenerator
import de.esd.zeiterfassung.repository.AppConfigurationRepository
import de.esd.zeiterfassung.repository.ArbeitsverhaeltnisRepository
import de.esd.zeiterfassung.repository.ZeiterfassungRepository

class ZeiterfassungViewModelFactory(private val context: Context) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val calendarConfigurationDao = AppDatabase.getDb(context).calendarConfigurationDao()
        val titleDao = AppDatabase.getDb(context).titleDao()
        val teamUpApi = TeamupServiceGenerator.getTeamUpApi(context)
        val configRepository = AppConfigurationRepository(calendarConfigurationDao, titleDao, teamUpApi)
        val zeiterfassungRepository = ZeiterfassungRepository(configRepository, ArbeitsverhaeltnisRepository(teamUpApi))

        return modelClass.getConstructor(ZeiterfassungRepository::class.java).newInstance(zeiterfassungRepository)
    }


}