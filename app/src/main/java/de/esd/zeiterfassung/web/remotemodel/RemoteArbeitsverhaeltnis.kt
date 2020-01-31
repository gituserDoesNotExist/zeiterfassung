package de.esd.zeiterfassung.web.remotemodel

import de.esd.zeiterfassung.core.Arbeitszeit
import org.apache.commons.lang3.StringUtils.isNotBlank
import org.threeten.bp.LocalDate
import java.math.BigInteger

class RemoteArbeitsverhaeltnis {

    var title: String = ""
    var kommentar: String = ""
    lateinit var datum: LocalDate
    lateinit var leistungsnehmerKey: String
    var leistungserbringerKey: String? = null

    var arbeitszeit: Arbeitszeit? = null
    var taetigkeitKey: String? = null
    var fahrzeugKey: String? = null
    var anbaugeraetKey: String? = null

    var stueckzahl: BigInteger? = null
    var produktKey: String? = null

    fun isZeitVerhaeltnis(): Boolean {
        return isNotBlank(anbaugeraetKey) || isNotBlank(fahrzeugKey) || isNotBlank(taetigkeitKey)
    }

    fun isStueckVerhaeltnis(): Boolean {
        return stueckzahl != null || isNotBlank(produktKey)
    }

}