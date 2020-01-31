package de.esd.zeiterfassung.web.remotemodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class EventResponseDTO {

    @SerializedName("event")
    @Expose
    lateinit var event: Event
    @SerializedName("undo_id")
    @Expose
    lateinit var undoId: String

}