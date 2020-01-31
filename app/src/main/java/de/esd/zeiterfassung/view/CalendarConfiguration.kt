package de.esd.zeiterfassung.view

import de.esd.zeiterfassung.config.Maschine
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.config.Produkt
import de.esd.zeiterfassung.config.Taetigkeit

data class CalendarConfiguration(var appUser: Person,
                                 var teatigkeiten: List<Taetigkeit>,
                                 var teilnehmer: List<Person>,
                                 var fahrzeuge: List<Maschine>,
                                 var anbaugeraete: List<Maschine>,
                                 val produkte: List<Produkt>)