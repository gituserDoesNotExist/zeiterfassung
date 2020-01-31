package de.esd.zeiterfassung.view

import de.esd.zeiterfassung.view.LocalDateConverter
import org.threeten.bp.LocalDate

class DateSuchkriterium constructor(private val date: LocalDate) : Suchkriterium<LocalDate> {

    override fun anzeigename(): String {
        return LocalDateConverter.dateToString(date)
    }

    fun getSuchkriterium(): LocalDate {
        return date
    }

}