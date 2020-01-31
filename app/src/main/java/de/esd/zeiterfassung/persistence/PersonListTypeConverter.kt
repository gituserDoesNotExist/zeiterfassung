package de.esd.zeiterfassung.persistence

import androidx.room.TypeConverter
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.config.Person

class PersonListTypeConverter {

    @TypeConverter
    fun fromPersonList(values: List<Person>): String {
        return ObjectMapper.objectToJson(values)
    }

    @TypeConverter
    fun toPersonList(value: String): List<Person> {
        return ObjectMapper.fromJson(value)
    }

}