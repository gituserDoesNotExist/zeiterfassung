package de.esd.zeiterfassung.view

import android.content.Context
import android.widget.ArrayAdapter
import de.esd.zeiterfassung.R

class TitlesArrayAdapter(context: Context, titles: List<String>) :
    ArrayAdapter<String>(context, R.layout.item_list_popup_window, titles)