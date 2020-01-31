package de.esd.zeiterfassung.config

data class RemoteCalendarMetadata(val taetigkeiten: List<Taetigkeit>, val teilnehmer: List<Person>,
                                  val fahrzeuge: List<Fahrzeug>, val anbaugeraete: List<Anbaugeraet>,
                                  var produkte: List<Produkt>)