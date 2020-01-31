package de.esd.zeiterfassung.view

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import de.esd.zeiterfassung.InvalidUserInputException
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.repository.ZeiterfassungRepository
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import ru.gildor.databinding.observables.ObservableString

class AppConfigurationViewModel(private val zeiterfassungRepository: ZeiterfassungRepository) : ViewModel() {

    var calendarConfig = zeiterfassungRepository.getConfiguration()
    lateinit var titles: List<String>

    var selectedAppUser = Person()
    var selectedAppUserForAnzeige = ObservableString()
    var appUserMissing = ObservableBoolean()

    private var fetchTitleDisposable: Disposable? = null


    fun loadTitles() {
        fetchTitleDisposable = zeiterfassungRepository.getTitles().subscribe(Consumer { titles = it })
    }

    fun downloadRemoteConfig(): Single<CalendarConfiguration> {
        return zeiterfassungRepository.downloadRemoteConfiguration()
    }


    fun saveAppUser(): Single<Person> {
        appUserMissing.set(selectedAppUser.name.isBlank())
        return if (appUserMissing.get()) {
            Single.fromCallable { throw InvalidUserInputException("Bitte App-User auswählen") }
        } else {
            zeiterfassungRepository.saveAppUser(selectedAppUser)
        }
    }

    fun selectAppUser(person: Person) {
        selectedAppUser = person
        selectedAppUserForAnzeige.set(person.name)
    }

    override fun onCleared() {
        super.onCleared()
        fetchTitleDisposable?.dispose()
    }


}

