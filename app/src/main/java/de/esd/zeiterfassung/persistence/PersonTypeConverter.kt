package de.esd.zeiterfassung.persistence

import androidx.room.TypeConverter
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.config.Person

class PersonTypeConverter {

    @TypeConverter
    fun fromPerson(value: Person): String {
        return ObjectMapper.objectToJson(value)
    }

    @TypeConverter
    fun toPerson(value: String): Person {
        return ObjectMapper.fromJson(value)
    }

}