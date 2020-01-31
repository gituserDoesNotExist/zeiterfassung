package de.esd.zeiterfassung.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ListPopupWindow
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.EsdDatePickerDialog
import de.esd.zeiterfassung.KeineAuswahl
import de.esd.zeiterfassung.config.Person
import de.esd.zeiterfassung.config.Produkt
import de.esd.zeiterfassung.databinding.FragmentUpsertStueckArbeitsverhaeltnisBinding
import org.threeten.bp.LocalDate


abstract class UpsertStueckArbeitsverhaeltnisFragment : UpsertArbeitsverhaeltnisFragment() {


    protected lateinit var viewModel: UpsertStueckArbeitsverhaeltnisViewModel

    private lateinit var produktListPopupWindow: ListPopupWindow


    override fun createRequiredListPopupWindows(activity: BaseActivity, calendarConfig: CalendarConfiguration) {
        produktListPopupWindow = createListPopupWindowProdukt(activity, calendarConfig.produkte)
    }

    override fun initArbeitsverhaeltnisViewModel(activity: BaseActivity) {
        viewModel = activity.provideViewModel(UpsertStueckArbeitsverhaeltnisViewModel::class.java)
    }

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, titlesAdapter: TitlesArrayAdapter?): View {
        val binding = FragmentUpsertStueckArbeitsverhaeltnisBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.upsertArbeitsverhaeltnisDetailsFragment = this

        binding.autocompleteTitle.apply {
            setAdapter(titlesAdapter)
            threshold = 1
        }
        return binding.root
    }


    override fun updateLeistungserbringerListener(entries: List<Person>): (Int) -> Unit {
        return {
            if (it == 0) viewModel.setLeistungserbringer(Person())
            else viewModel.setLeistungserbringer(entries[it - 1])
            leistungserbringerListPopupWindow.dismiss()
        }
    }

    override fun updateLeistungsnehmerListener(entries: List<Person>): (Int) -> Unit {
        return {
            if (it == 0) viewModel.setLeistungsnehmer(Person())
            else viewModel.setLeistungsnehmer(entries[it - 1])
            leistungsnehmerListPopupWindow.dismiss()
        }
    }



    private fun createListPopupWindowProdukt(it: AppCompatActivity, entries: List<Produkt>): ListPopupWindow {
        val updateModelListener: (Int) -> Unit = {
            if (it == 0) viewModel.setProdukt(Produkt())
            else viewModel.setProdukt(entries[it - 1])
            produktListPopupWindow.dismiss()
        }
        val dropdownEntries = mutableListOf(KeineAuswahl.value).apply { addAll(entries.map { it.name }) }
        return createListPopupWindow(it, dropdownEntries, updateModelListener)
    }


    fun openDatePickerDialog() {
        val crrntDate = viewModel.stueckArbeitsverhaeltnis.datum
        val dateSetListener: (LocalDate) -> Unit = {
            viewModel.setDatum(it)
        }
        activity?.let {
            EsdDatePickerDialog(it, dateSetListener, crrntDate).show()
        }
    }


    fun openProduktListPopUp(editTextView: View) {
        produktListPopupWindow.apply { this.anchorView = editTextView }.show()
    }


}
