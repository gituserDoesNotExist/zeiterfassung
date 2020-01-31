package de.esd.zeiterfassung.view

import androidx.lifecycle.ViewModel
import de.esd.zeiterfassung.repository.ZeiterfassungRepository
import io.reactivex.Single

class StartZeiterfassungViewModel(private val zeiterfassungRepository: ZeiterfassungRepository) : ViewModel() {

    fun existsConfiguration(): Single<Boolean> {
        return zeiterfassungRepository.existsConfiguration()
    }


}