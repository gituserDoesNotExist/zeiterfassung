package de.esd.zeiterfassung

import java.math.BigDecimal
import java.math.RoundingMode

object EsdBigDecimalFormat {

    fun default(value: BigDecimal) : BigDecimal {
        return value.setScale(1, RoundingMode.DOWN)
    }

}