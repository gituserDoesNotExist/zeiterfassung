package de.esd.zeiterfassung.view

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import de.esd.zeiterfassung.InvalidUserInputException
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.repository.ZeiterfassungRepository
import io.reactivex.Single

abstract class UpsertArbeitsverhaeltnisViewModel(protected val zeiterfassungRepository: ZeiterfassungRepository) :
    ViewModel() {

    val isUpdateMode = ObservableBoolean()
    var editable = ObservableBoolean()
    var titleMissing = ObservableBoolean()

    val config = zeiterfassungRepository.getConfiguration()


    lateinit var eventInfo: EventInfo

    fun initEventInfo(eventInfo: EventInfo) {
        this.eventInfo = eventInfo.copy()
    }

    protected fun <T> performUpsertOperation(upsertOperation: () -> Single<T>): Single<T> {
        return if (!validate()) {
            Single.fromCallable { throw InvalidUserInputException("Es fehlen noch Daten") }
        } else {
            upsertOperation()
        }
    }

    abstract fun updateArbeitsverhaeltnis(): Single<String>

    abstract fun addArbeitsverhaeltnis(): Single<Long>

    protected abstract fun validate(): Boolean


}