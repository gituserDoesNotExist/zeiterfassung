package de.esd.zeiterfassung.view


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.DividerItemDecoration
import de.esd.zeiterfassung.R
import de.esd.zeiterfassung.core.Arbeitseinsaetze
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import de.esd.zeiterfassung.databinding.FragmentArbeitsverhaeltnisUebersichtBinding
import kotlinx.android.synthetic.main.fragment_arbeitsverhaeltnis_uebersicht.view.*


class ArbeitsverhaltnisUebersichtFragment : Fragment() {


    private lateinit var arbeitsverhaeltnisViewModel: ArbeitsverhaeltnisUebersichtViewModel
    private lateinit var sharedZeitArbeitsverhaeltnisVM: SharedZeitArbeitsverhaeltnisViewModel
    private lateinit var sharedStueckVerhaeltnisVM: SharedStueckArbeitsverhaeltnisViewModel
    private lateinit var filtersViewModel: FiltersViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding = FragmentArbeitsverhaeltnisUebersichtBinding.inflate(inflater, container, false)
        val rootView = binding.root
        (activity as? BaseActivity)?.let { activity ->
            activity.supportActionBar?.title = resources.getString(R.string.title_fragment_arbeitsvheraeltnis_ubersicht)
            configureNetworkErrorHandling(activity)
            initializeViewModel(activity)
            loadArbeitsverhaeltnisse()
            addFiltersRecyclerView(rootView, activity)
            arbeitsverhaeltnisViewModel.arbeitseinsaetze.observe(this, Observer {
                addEventsRecyclerView(rootView, activity, it)
            })

        }
        binding.arbeitsverhaltnisUebersichtFragment = this
        binding.viewModel = arbeitsverhaeltnisViewModel

        return rootView
    }

    private fun configureNetworkErrorHandling(activity: BaseActivity) {
        activity.hideProgressbarCallback = {
            arbeitsverhaeltnisViewModel.showProgressbar.set(false)
        }
        activity.reloadCallback = {
            arbeitsverhaeltnisViewModel.loadArbeitsverhaeltnisse(filtersViewModel.suchkriterien, true)
        }
    }

    private fun initializeViewModel(activity: BaseActivity) {
        filtersViewModel = activity.provideViewModel(FiltersViewModel::class.java)
        arbeitsverhaeltnisViewModel = activity.provideViewModel(ArbeitsverhaeltnisUebersichtViewModel::class.java)
        sharedZeitArbeitsverhaeltnisVM = activity.provideViewModel(SharedZeitArbeitsverhaeltnisViewModel::class.java)
        sharedStueckVerhaeltnisVM = activity.provideViewModel(SharedStueckArbeitsverhaeltnisViewModel::class.java)
    }

    private fun loadArbeitsverhaeltnisse() {
        arbeitsverhaeltnisViewModel.loadArbeitsverhaeltnisse(filtersViewModel.suchkriterien, true)
    }

    private fun addEventsRecyclerView(root: View, activity: AppCompatActivity, arbeitseinsaetze: Arbeitseinsaetze) {
        val recyclerView = root.recycler_view_arbeitsverhaeltnisse
        recyclerView.adapter = ArbeitseinsaetzeRecyclerViewAdapter(arbeitseinsaetze).apply {
            this.onItemClickListener = View.OnClickListener { v ->
                val viewHolder = v.tag as ArbeitseinsaetzeRecyclerViewAdapter.ItemViewHolder
                openMatchingEditFragment(arbeitseinsaetze, viewHolder.arbeitsverhaeltnisRemoteId)
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(this.context))

    }

    private fun openMatchingEditFragment(arbeitseinsaetze: Arbeitseinsaetze, eventId: String) {
        arbeitseinsaetze.findById(eventId)?.let {
            val verhaeltnis = it.arbeitsverhaeltnis
            val navController = findNavController()
            if (verhaeltnis is ZeitArbeitsverhaeltnis) {
                sharedZeitArbeitsverhaeltnisVM.eventInfo = it.eventInfo
                sharedZeitArbeitsverhaeltnisVM.currentArbeitsverhaeltnis = verhaeltnis
                ZeiterfassungNavigation.getNavigation(navController).fromUebersichtToEditZeitArbeitsverhaeltnis()
            } else if (verhaeltnis is StueckArbeitsverhaeltnis) {
                sharedStueckVerhaeltnisVM.eventInfo = it.eventInfo
                sharedStueckVerhaeltnisVM.currentArbeitsverhaeltnis = verhaeltnis
                ZeiterfassungNavigation.getNavigation(navController).fromUebersichtToEditStueckArbeitsverhaeltnis()
            }
        }
    }

    private fun addFiltersRecyclerView(root: View, activity: AppCompatActivity) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view_filters)

        recyclerView.adapter = FiltersRecyclerViewAdapter(filtersViewModel.suchkriterien) {
            filtersViewModel.removeFilter(it)
            arbeitsverhaeltnisViewModel.loadArbeitsverhaeltnisse(filtersViewModel.suchkriterien, true)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_arbeitsverhaeltnis_uebersicht, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_arbeitshverhaeltnisse -> openFilterFragment()
            R.id.action_refresh_arbeitshverhaeltnisse -> loadArbeitsverhaeltnisse()
        }
        return false
    }

    private fun openFilterFragment() {
        ZeiterfassungNavigation.getNavigation(findNavController()).fromUebersichtToSuchfilter()
    }

    fun openAddArbeitsverhaeltnisFragment() {
        ZeiterfassungNavigation.getNavigation(findNavController()).fromUebersichtToAddArbeitsverhaeltnis()
    }


}
