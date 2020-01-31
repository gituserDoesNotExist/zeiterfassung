package de.esd.zeiterfassung.config

import java.math.BigDecimal

class Person(override val key: String = "", val name: String = "",
             override val stundensatz: BigDecimal = BigDecimal.ZERO) : Abrechenbar