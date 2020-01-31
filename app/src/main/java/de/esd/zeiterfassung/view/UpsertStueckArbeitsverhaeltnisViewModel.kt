package de.esd.zeiterfassung.view

import androidx.databinding.ObservableBoolean
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.config.Produkt
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import de.esd.zeiterfassung.repository.ZeiterfassungRepository
import io.reactivex.Single
import org.threeten.bp.LocalDate

class UpsertStueckArbeitsverhaeltnisViewModel(zeiterfassungRepository: ZeiterfassungRepository) :
    UpsertArbeitsverhaeltnisViewModel(zeiterfassungRepository) {

    var anzahlMissing = ObservableBoolean()
    var produktnameMissing = ObservableBoolean()

    lateinit var stueckArbeitsverhaeltnis: StueckArbeitsverhaeltnis
    var arbeitsverhaeltnisForAnzeige = StueckArbeitsverhaeltnisForAnzeige()

    fun initEventInfoAndArbeitsverhaeltnis(eventInfo: EventInfo, arbeitsverhaeltnis: StueckArbeitsverhaeltnis) {
        super.initEventInfo(eventInfo)
        this.stueckArbeitsverhaeltnis = arbeitsverhaeltnis.copy()
        arbeitsverhaeltnisForAnzeige.copyValuesFromArbeitsverhaeltnis(this.stueckArbeitsverhaeltnis)
    }


    override fun updateArbeitsverhaeltnis(): Single<String> {
        return performUpsertOperation {
            takeRelevantValuesFromArbeitseinsatzForAnzeige()
            zeiterfassungRepository.updateStueckArbeitsverhaeltnis(stueckArbeitsverhaeltnis, eventInfo)//
        }
    }

    override fun addArbeitsverhaeltnis(): Single<Long> {
        return performUpsertOperation {
            takeRelevantValuesFromArbeitseinsatzForAnzeige()
            zeiterfassungRepository.addStueckArbeitsverhaeltnisToRemoteCalendar(stueckArbeitsverhaeltnis)
        }
    }

    override fun validate(): Boolean {
        val stueckzahlSet = arbeitsverhaeltnisForAnzeige.stueckzahl.get() > 0
        val produktSet = arbeitsverhaeltnisForAnzeige.produktName.get().isNotBlank()
        val titleSet = arbeitsverhaeltnisForAnzeige.title.get().isNotBlank()
        anzahlMissing.set(!stueckzahlSet)
        produktnameMissing.set(!produktSet)
        titleMissing.set(!titleSet)
        return stueckzahlSet && produktSet && titleSet
    }


    fun setLeistungsnehmer(value: Person) {
        stueckArbeitsverhaeltnis.leistungsnehmer = value
        arbeitsverhaeltnisForAnzeige.leistungsnehmer.set(value.name)
    }


    fun setLeistungserbringer(value: Person) {
        stueckArbeitsverhaeltnis.leistungserbringer = value
        arbeitsverhaeltnisForAnzeige.leistungserbringer.set(value.name)
    }

    fun setProdukt(value: Produkt) {
        stueckArbeitsverhaeltnis.produkt = value
        arbeitsverhaeltnisForAnzeige.produktName.set(value.name)
    }


    fun setDatum(value: LocalDate) {
        stueckArbeitsverhaeltnis.datum = value
        arbeitsverhaeltnisForAnzeige.datum.set(value)
    }

    private fun takeRelevantValuesFromArbeitseinsatzForAnzeige() {
        stueckArbeitsverhaeltnis.title = arbeitsverhaeltnisForAnzeige.title.get()
        stueckArbeitsverhaeltnis.kommentar = arbeitsverhaeltnisForAnzeige.kommentar.get()
        stueckArbeitsverhaeltnis.stueckzahl = arbeitsverhaeltnisForAnzeige.stueckzahl.get()
    }

    fun deleteArbeitsverhaeltnis(): Single<String> {
        return zeiterfassungRepository.deleteArbeitsverhaeltnis(eventInfo)
    }

}