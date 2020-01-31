package de.esd.zeiterfassung.persistence

import androidx.room.TypeConverter
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.config.Fahrzeug

class FahrzeugListTypeConverter {

    @TypeConverter
    fun fromFahrzeugList(values: List<Fahrzeug>): String {
        return ObjectMapper.objectToJson(values)
    }

    @TypeConverter
    fun toFahrzeugList(value: String): List<Fahrzeug> {
        return ObjectMapper.fromJson(value)
    }

}