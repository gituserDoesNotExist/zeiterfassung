package de.esd.zeiterfassung

import android.content.Context

class ConfirmDeleteDialog(context: Context?, onConfirmListener: () -> Unit) :
    EsdAlertDialog(context, "", onConfirmListener) {

    init {
        setTitle(context?.resources?.getString(R.string.loeschen))
    }


}