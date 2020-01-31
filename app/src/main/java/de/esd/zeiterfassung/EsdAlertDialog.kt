package de.esd.zeiterfassung

import android.app.AlertDialog
import android.content.Context

open class EsdAlertDialog(context: Context?, title: String, onConfirmListener: () -> Unit) :
    AlertDialog.Builder(context, R.style.Theme_Esd_AlertDialog) {

    init {
        this.setTitle(title).setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                onConfirmListener()
            }.setNegativeButton(android.R.string.no, null)

    }

}