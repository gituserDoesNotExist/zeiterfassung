package de.esd.zeiterfassung.persistence

import androidx.room.TypeConverter
import de.esd.zeiterfassung.ObjectMapper
import de.esd.zeiterfassung.config.Produkt

class ProduktListTypeConverter {

    @TypeConverter
    fun fromProduktList(values: List<Produkt>): String {
        return ObjectMapper.objectToJson(values)
    }

    @TypeConverter
    fun toProduktList(value: String): List<Produkt> {
        return ObjectMapper.fromJson(value)
    }

}