package de.esd.zeiterfassung.config

import java.math.BigDecimal

interface Abrechenbar {

    val key: String
    val stundensatz: BigDecimal

}