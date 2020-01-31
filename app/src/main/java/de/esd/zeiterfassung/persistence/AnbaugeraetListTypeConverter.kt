package de.esd.zeiterfassung.persistence

import androidx.room.TypeConverter
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.config.Anbaugeraet

class AnbaugeraetListTypeConverter {

    @TypeConverter
    fun fromAnbaugeraetList(values: List<Anbaugeraet>): String {
        return ObjectMapper.objectToJson(values)
    }

    @TypeConverter
    fun toAnbaugeraetList(value: String): List<Anbaugeraet> {
        return ObjectMapper.fromJson(value)
    }

}