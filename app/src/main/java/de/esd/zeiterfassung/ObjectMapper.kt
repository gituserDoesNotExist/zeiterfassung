package de.esd.zeiterfassung

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ObjectMapper {

    fun <T> objectToJson(value: T): String {
        return Gson().toJson(value)
    }

    inline fun <reified T> fromJson(json: String): T {
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }


}