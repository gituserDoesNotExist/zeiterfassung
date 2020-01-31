package de.esd.zeiterfassung.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import de.esd.zeiterfassung.DialogOpener
import de.esd.zeiterfassung.EsdDatePickerDialog
import de.esd.zeiterfassung.R
import de.esd.zeiterfassung.databinding.FragmentSuchfilterBinding
import org.threeten.bp.LocalDate

class SuchfilterFragment : Fragment() {

    private lateinit var filtersViewModel: FiltersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as? AppCompatActivity)?.let {
            it.supportActionBar?.title = resources.getString(R.string.title_fragment_suchfilter)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            filtersViewModel = ViewModelProviders.of(it).get(FiltersViewModel::class.java)
        }
        val binding = FragmentSuchfilterBinding.inflate(inflater, container, false)


        binding.suchfilterFragment = this
        binding.viewModel = filtersViewModel

        return binding.root
    }


    fun openDatePickerStartDate() {
        val dateSetListener: (LocalDate) -> Unit = { date ->
            filtersViewModel.updateStartDate(date)
        }
        activity?.let { EsdDatePickerDialog(it, dateSetListener,
            filtersViewModel.getStartDate()).show() }
    }

    fun openDatePickerEndDate() {
        val dateSetListener: (LocalDate) -> Unit = { date ->
            filtersViewModel.updateEndDate(date)
        }
        activity?.let { EsdDatePickerDialog(it, dateSetListener,
            filtersViewModel.getEndDate()).show() }
    }

    fun openSelectLeistungsnehmerDialog() {
        activity?.let {
            DialogOpener.openDialog(it, SelectLeistungsnehmerDialog(), "dialog_select_filter")
        }
    }

    fun openSelectLeistungserbringerDialog() {
        activity?.let {
            DialogOpener.openDialog(it, SelectLeistungserbringerDialog(), "dialog_select_leistungserbringer")
        }
    }

    fun openSelectTaetigkeitDialog() {
        activity?.let {
            DialogOpener.openDialog(it, SelectTaetigkeitDialog(), "dialog_select_teatigkeit")
        }
    }

    fun applyFilters() {
        ZeiterfassungNavigation.getNavigation(findNavController()).toUebersicht()
    }


}
