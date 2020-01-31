package de.esd.zeiterfassung.core

import de.esd.zeiterfassung.EsdBigDecimalFormat
import java.math.BigDecimal

class Arbeitszeit(decimalHours: BigDecimal) {

    constructor(value: String) : this(BigDecimal(value))

    var dauer: BigDecimal = EsdBigDecimalFormat.default(decimalHours)

    fun getTimeInMinutes(): Long {
        return dauer.times(BigDecimal(60)).longValueExact()
    }

    override fun toString(): String {
        return String.format("%.1f Stunden",dauer)
    }
}