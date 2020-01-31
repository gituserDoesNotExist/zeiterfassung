package de.esd.zeiterfassung.view

import androidx.databinding.ObservableBoolean
import de.esd.zeiterfassung.config.Maschine
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.config.Taetigkeit
import de.esd.zeiterfassung.core.Arbeitszeit
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.repository.ZeiterfassungRepository
import io.reactivex.Single
import org.threeten.bp.LocalDate

class UpsertZeitArbeitsverhaeltnisViewModel(zeiterfassungRepository: ZeiterfassungRepository) :
    UpsertArbeitsverhaeltnisViewModel(zeiterfassungRepository) {

    var taetigkeitMissing = ObservableBoolean()
    var dauerMissing = ObservableBoolean()

    lateinit var zeitArbeitsverhaeltnis: ZeitArbeitsverhaeltnis
    var arbeitsverhaeltnisForAnzeige = ZeitArbeitsverhaeltnisForAnzeige()


    fun initEventInfoAndArbeitsverhaeltnis(eventInfo: EventInfo, arbeitsverhaeltnis: ZeitArbeitsverhaeltnis) {
        super.initEventInfo(eventInfo)
        this.zeitArbeitsverhaeltnis = arbeitsverhaeltnis.copy()
        arbeitsverhaeltnisForAnzeige.copyValuesFromArbeitsverhaeltnis(this.zeitArbeitsverhaeltnis)
    }

    override fun resetValidation() {
        super.resetValidation()
        taetigkeitMissing.set(false)
        dauerMissing.set(false)
    }

    override fun updateArbeitsverhaeltnis(): Single<String> {
        return performUpsertOperation {
            takeRelevantValuesFromArbeitseinsatzForAnzeige()
            zeiterfassungRepository.updateZeitArbeitsverhaeltnis(zeitArbeitsverhaeltnis, eventInfo)//
        }
    }

    override fun addArbeitsverhaeltnis(): Single<Long> {
        return performUpsertOperation {
            takeRelevantValuesFromArbeitseinsatzForAnzeige()
            zeiterfassungRepository.addZeitArbeitsverhaeltnisToRemoteCalendar(zeitArbeitsverhaeltnis)//
        }
    }

    override fun validate(): Boolean {
        val taetigkeitSet = arbeitsverhaeltnisForAnzeige.taetigkeit.get().isNotBlank()
        val dauerGreaterThanZero = Arbeitszeit(arbeitsverhaeltnisForAnzeige.arbeitszeit.get()).getTimeInMinutes() > 0
        val titleSet = arbeitsverhaeltnisForAnzeige.title.get().isNotBlank()
        val leistungsnehmerSet = arbeitsverhaeltnisForAnzeige.leistungsnehmer.get().isNotBlank()
        taetigkeitMissing.set(!taetigkeitSet)
        dauerMissing.set(!dauerGreaterThanZero)
        titleMissing.set(!titleSet)
        leistungsnehmerMissing.set(!leistungsnehmerSet)
        return taetigkeitSet && dauerGreaterThanZero && titleSet && leistungsnehmerSet
    }


    fun setLeistungsnehmer(value: Person) {
        zeitArbeitsverhaeltnis.leistungsnehmer = value
        arbeitsverhaeltnisForAnzeige.leistungsnehmer.set(value.name)
    }


    fun setLeistungserbringer(value: Person) {
        zeitArbeitsverhaeltnis.leistungserbringer = value
        arbeitsverhaeltnisForAnzeige.leistungserbringer.set(value.name)
    }


    fun setTaetigkeit(value: Taetigkeit) {
        zeitArbeitsverhaeltnis.taetigkeit = value
        arbeitsverhaeltnisForAnzeige.taetigkeit.set(value.bezeichnung)
    }


    fun setFahrzeug(value: Maschine) {
        zeitArbeitsverhaeltnis.fahrzeug = value
        arbeitsverhaeltnisForAnzeige.fahrzeug.set(value.bezeichnung)
    }


    fun setAnbaugaeraet(value: Maschine) {
        zeitArbeitsverhaeltnis.anbaugeraet = value
        arbeitsverhaeltnisForAnzeige.anbaugeraet.set(value.bezeichnung)
    }

    fun setDatum(value: LocalDate) {
        zeitArbeitsverhaeltnis.datum = value
        arbeitsverhaeltnisForAnzeige.datum.set(value)
    }

    private fun takeRelevantValuesFromArbeitseinsatzForAnzeige() {
        zeitArbeitsverhaeltnis.title = arbeitsverhaeltnisForAnzeige.title.get()
        zeitArbeitsverhaeltnis.kommentar = arbeitsverhaeltnisForAnzeige.kommentar.get()
        arbeitsverhaeltnisForAnzeige.arbeitszeit.get().let {
            zeitArbeitsverhaeltnis.arbeitszeit = Arbeitszeit(it)
        }
    }

    fun deleteArbeitsverhaeltnis(): Single<String> {
        return zeiterfassungRepository.deleteArbeitsverhaeltnis(eventInfo)
    }

}