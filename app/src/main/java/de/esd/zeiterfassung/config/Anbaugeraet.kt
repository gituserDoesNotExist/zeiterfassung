package de.esd.zeiterfassung.config

import java.math.BigDecimal

class Anbaugeraet : Maschine {

    constructor() : super()

    constructor(key: String, bezeichnung: String, stundensatz: BigDecimal) : super(key,bezeichnung,stundensatz)

}