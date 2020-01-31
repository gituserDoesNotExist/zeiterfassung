package de.esd.zeiterfassung.persistence

import androidx.room.TypeConverter
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.config.Taetigkeit

class TaetigkeitListTypeConverter {

    @TypeConverter
    fun fromTaetigkeitList(values: List<Taetigkeit>): String {
        return ObjectMapper.objectToJson(values)
    }

    @TypeConverter
    fun toTaetigkeitList(value: String): List<Taetigkeit> {
        return ObjectMapper.fromJson(value)
    }

}