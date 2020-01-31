package de.esd.zeiterfassung.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.EsdDatePickerDialog
import de.esd.zeiterfassung.KeineAuswahl
import de.esd.zeiterfassung.config.*
import de.esd.zeiterfassung.databinding.FragmentUpsertZeitArbeitsverhaeltnisBinding
import org.threeten.bp.LocalDate


abstract class UpsertZeitArbeitsverhaeltnisFragment : UpsertArbeitsverhaeltnisFragment() {


    protected lateinit var upsertViewModel: UpsertZeitArbeitsverhaeltnisViewModel

    private lateinit var fahrzeugListPopupWindow: ListPopupWindow
    private lateinit var anbaugeraetListPopupWindow: ListPopupWindow
    private lateinit var taetigkeitListPopupWindow: ListPopupWindow


    override fun createRequiredListPopupWindows(activity: BaseActivity, calendarConfig: CalendarConfiguration) {
        fahrzeugListPopupWindow = createListPopupWindowFahrzeug(activity, calendarConfig.fahrzeuge)
        anbaugeraetListPopupWindow = createListPopupWindowAnaugeraet(activity, calendarConfig.anbaugeraete)
        taetigkeitListPopupWindow = createListPopupWindowTaetigkeit(activity, calendarConfig.teatigkeiten)
    }

    override fun initArbeitsverhaeltnisViewModel(activity: BaseActivity) {
        upsertViewModel = activity.provideViewModel(UpsertZeitArbeitsverhaeltnisViewModel::class.java)
    }


    override fun createView(inflater: LayoutInflater, container: ViewGroup?, titlesAdapter: TitlesArrayAdapter?): View {
        val binding = FragmentUpsertZeitArbeitsverhaeltnisBinding.inflate(inflater, container, false)
        binding.viewModel = upsertViewModel
        binding.upsertArbeitsverhaeltnisDetailsFragment = this

        binding.autocompleteTitle.apply {
            setAdapter(titlesAdapter)
            threshold = 1
        }
        return binding.root
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentUpsertZeitArbeitsverhaeltnisBinding.inflate(inflater, container, false)
        binding.viewModel = upsertViewModel
        binding.upsertArbeitsverhaeltnisDetailsFragment = this

        (activity as? BaseActivity)?.let {
            val adapter = TitlesArrayAdapter(it.applicationContext, appConfigurationViewModel.titles)
            binding.autocompleteTitle.apply {
                setAdapter(adapter)
                threshold = 1
            }
        }

        return binding.root
    }


    override fun updateLeistungserbringerListener(entries: List<Person>): (Int) -> Unit {
        return {
            if (it == 0) upsertViewModel.setLeistungserbringer(Person())
            else upsertViewModel.setLeistungserbringer(entries[it - 1])
            leistungserbringerListPopupWindow.dismiss()
        }
    }

    override fun updateLeistungsnehmerListener(entries: List<Person>): (Int) -> Unit {
        return {
            if (it == 0) upsertViewModel.setLeistungsnehmer(Person())
            else upsertViewModel.setLeistungsnehmer(entries[it - 1])
            leistungsnehmerListPopupWindow.dismiss()
        }
    }


    private fun createListPopupWindowFahrzeug(it: AppCompatActivity, entries: List<Maschine>): ListPopupWindow {
        val updateModelListener: (Int) -> Unit = {
            if (it == 0) upsertViewModel.setFahrzeug(Fahrzeug())
            else upsertViewModel.setFahrzeug(entries[it - 1])
            fahrzeugListPopupWindow.dismiss()
        }
        val dropdownEntries = mutableListOf(KeineAuswahl.value).apply { addAll(entries.map { it.bezeichnung }) }
        return createListPopupWindow(it, dropdownEntries, updateModelListener)
    }


    private fun createListPopupWindowAnaugeraet(it: AppCompatActivity, entries: List<Maschine>): ListPopupWindow {
        val updateModelListener: (Int) -> Unit = {
            if (it == 0) upsertViewModel.setAnbaugaeraet(Anbaugeraet())
            else upsertViewModel.setAnbaugaeraet(entries[it - 1])
            anbaugeraetListPopupWindow.dismiss()
        }
        val dropdownEntries = mutableListOf(KeineAuswahl.value).apply { addAll(entries.map { it.bezeichnung }) }
        return createListPopupWindow(it, dropdownEntries, updateModelListener)
    }


    private fun createListPopupWindowTaetigkeit(it: AppCompatActivity, entries: List<Taetigkeit>): ListPopupWindow {
        val updateModelListener: (Int) -> Unit = {
            if (it == 0) upsertViewModel.setTaetigkeit(Taetigkeit())
            else upsertViewModel.setTaetigkeit(entries[it - 1])
            taetigkeitListPopupWindow.dismiss()
        }
        val dropdownEntries = mutableListOf(KeineAuswahl.value).apply { addAll(entries.map { it.bezeichnung }) }
        return createListPopupWindow(it, dropdownEntries, updateModelListener)
    }


    fun openDatePickerDialog() {
        val crrntDate = upsertViewModel.zeitArbeitsverhaeltnis.datum
        val dateSetListener: (LocalDate) -> Unit = {
            upsertViewModel.setDatum(it)
        }
        activity?.let {
            EsdDatePickerDialog(it, dateSetListener, crrntDate).show()
        }
    }


    fun openFahrzeugListPopUp(editTextView: View) {
        fahrzeugListPopupWindow.apply { this.anchorView = editTextView }.show()
    }

    fun openMaschineListPopUp(editTextView: View) {
        anbaugeraetListPopupWindow.apply { this.anchorView = editTextView }.show()
    }


    fun openTaetigkeitListPopUp(editTextView: View) {
        taetigkeitListPopupWindow.apply { this.anchorView = editTextView }.show()
    }


}
