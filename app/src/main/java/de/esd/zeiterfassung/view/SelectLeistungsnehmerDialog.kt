package de.esd.zeiterfassung.view

import de.esd.zeiterfassung.R
import de.esd.zeiterfassung.config.Person

class SelectLeistungsnehmerDialog : SelectFilterDialog<Person>() {


    override fun createRecyclerViewAdapter(config: CalendarConfiguration): SelectFiltersRecyclerViewAdapter<Person> {
        val filterItems = extractFilterValuesFromConfig(config)
        return object : SelectFiltersRecyclerViewAdapter<Person>(filterItems) {
            override fun anzeigeValue(currentItem: CheckableFilterItem<Person>): String {
                return currentItem.item.name
            }

            override fun onCheckboxSelectedListener(): (Boolean, Person) -> Unit = { checked, item ->
                if (checked) filterViewModel.addSelectedItemLeistungsnehmer(item)
                else filterViewModel.removeSelectedItemLeistungsnehmer(item)
            }
        }
    }

        override fun dialogTitle(): String {
            return resources.getString(R.string.leistungsnehmer)
        }

    private fun extractFilterValuesFromConfig(config: CalendarConfiguration): List<CheckableFilterItem<Person>> {
        val allLeistungsnehmer = config.teilnehmer
        val selectedLeistungsnehmer = filterViewModel.suchkriterien.leistungsnehmerToFilter
        return allLeistungsnehmer.map {
            val alreadyPresent = selectedLeistungsnehmer.contains(it)
            if (alreadyPresent) CheckableFilterItem(true, it)
            else CheckableFilterItem(item = it)
        }
    }


    override fun saveSelectedFilterValues() {
        filterViewModel.saveSelectedLeistungsnehmerItems()
    }

    override fun initSelectedFilterValues() {
        filterViewModel.suchkriterien.leistungsnehmerToFilter.forEach {
            filterViewModel.addSelectedItemLeistungsnehmer(it)
        }
    }
}