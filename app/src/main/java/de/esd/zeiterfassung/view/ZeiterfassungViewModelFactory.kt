package de.esd.zeiterfassung.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.esd.zeiterfassung.persistence.AppDatabase
import de.esd.zeiterfassung.repository.AppConfigurationRepository
import de.esd.zeiterfassung.repository.ArbeitsverhaeltnisRepository
import de.esd.zeiterfassung.repository.ZeiterfassungRepository
import de.esd.zeiterfassung.web.TeamupServiceGenerator

class ZeiterfassungViewModelFactory(private val context: Context) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (hasEmptyConstructor(modelClass)) {
            modelClass.getConstructor().newInstance()
        } else {
            modelClass.getConstructor(ZeiterfassungRepository::class.java).newInstance(createZeiterfassungRepository())

        }
    }

    private fun <T : ViewModel?> hasEmptyConstructor(modelClass: Class<T>): Boolean {
        val isSharedZeitVM = modelClass.isAssignableFrom(SharedZeitArbeitsverhaeltnisViewModel::class.java)
        val isSharedStueckVM = modelClass.isAssignableFrom(SharedStueckArbeitsverhaeltnisViewModel::class.java)
        return isSharedZeitVM || isSharedStueckVM
    }

    private fun createZeiterfassungRepository(): ZeiterfassungRepository {
        val calendarConfigurationDao = AppDatabase.getDb(context).calendarConfigurationDao()
        val titleDao = AppDatabase.getDb(context).titleDao()
        val teamUpApi = TeamupServiceGenerator.getTeamUpApi(context)
        val configRepository = AppConfigurationRepository(calendarConfigurationDao, titleDao, teamUpApi)

        return ZeiterfassungRepository(configRepository, ArbeitsverhaeltnisRepository(teamUpApi))
    }


}