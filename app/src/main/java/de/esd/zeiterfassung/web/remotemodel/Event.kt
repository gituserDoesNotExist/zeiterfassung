package de.esd.zeiterfassung.web.remotemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Event {

    @SerializedName("id")
    @Expose
    lateinit var id: String
    @SerializedName("series_id")
    @Expose
    var seriesId: Any? = null
    @SerializedName("remote_id")
    @Expose
    var remoteId: Any? = null
    @SerializedName("subcalendar_id")
    @Expose
    var subcalendarId: Long? = null
    @SerializedName("subcalendar_ids")
    @Expose
    var subcalendarIds: List<Int>? = null
    @SerializedName("all_day")
    @Expose
    var allDay: Boolean? = null
    @SerializedName("rrule")
    @Expose
    var rrule: String? = null
    @SerializedName("title")
    @Expose
    lateinit var title: String
    @SerializedName("who")
    @Expose
    lateinit var who: String
    @SerializedName("location")
    @Expose
    var location: String? = null
    @SerializedName("notes")
    @Expose
    lateinit var notes: String
    @SerializedName("version")
    @Expose
    lateinit var version: String
    @SerializedName("readonly")
    @Expose
    var readonly: Boolean? = null
    @SerializedName("tz")
    @Expose
    var tz: Any? = null
    @SerializedName("start_dt")
    @Expose
    lateinit var startDt: String
    @SerializedName("end_dt")
    @Expose
    lateinit var endDt: String
    @SerializedName("ristart_dt")
    @Expose
    var ristartDt: Any? = null
    @SerializedName("rsstart_dt")
    @Expose
    var rsstartDt: Any? = null
    @SerializedName("creation_dt")
    @Expose
    lateinit var creationDt: String
    @SerializedName("update_dt")
    @Expose
    var updateDt: Any? = null
    @SerializedName("delete_dt")
    @Expose
    var deleteDt: Any? = null
    @SerializedName("custom")
    @Expose
    var custom: Custom? = null

}