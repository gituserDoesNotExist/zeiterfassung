package de.esd.zeiterfassung.view


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.ConfirmDeleteDialog
import de.esd.zeiterfassung.R
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer


class EditStueckArbeitsverhaeltnisFragment : UpsertStueckArbeitsverhaeltnisFragment() {


    private var updateArbeitsverhaeltnisDisposable: Disposable? = null
    private lateinit var sharedVerhaeltnisViewModel: SharedStueckArbeitsverhaeltnisViewModel
    private var deleteArbeitsverhaeltnisDisposable: Disposable? = null

    override fun fragmentTitle(): String {
        return resources.getString(R.string.title_edit_arbeitsverhaeltnis_fragment)
    }

    override fun initArbeitsverhaeltnis(activity: BaseActivity) {
        sharedVerhaeltnisViewModel = activity.provideViewModel(SharedStueckArbeitsverhaeltnisViewModel::class.java)
        val eventInfo = sharedVerhaeltnisViewModel.eventInfo
        val arbeitsverhaeltnis = sharedVerhaeltnisViewModel.currentArbeitsverhaeltnis
        viewModel.initEventInfoAndArbeitsverhaeltnis(eventInfo, arbeitsverhaeltnis)
    }

    override fun prepareView(rootView: View, config: CalendarConfiguration) {
        setHasOptionsMenu(true)
        viewModel.isUpdateMode.set(true)
        viewModel.editable.set(false)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_arbeitsverhaeltnis_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete_arbeitsverhaeltnis -> {
                ConfirmDeleteDialog(this.context) {
                    confirmDeleteListener(findNavController())
                }.show()
            }
            R.id.action_edit_arbeitsverhaeltnis -> {
                viewModel.editable.set(true)
            }
        }
        return false
    }

    private fun confirmDeleteListener(navController: NavController) {
        deleteArbeitsverhaeltnisDisposable = viewModel.deleteArbeitsverhaeltnis()//
            .subscribe(Consumer<String> {
                ZeiterfassungNavigation.getNavigation(navController).toUebersicht()
            })
    }


    override fun upsert(): Single<String> {
        return viewModel.updateArbeitsverhaeltnis()//
    }

    override fun onStop() {
        super.onStop()
        updateArbeitsverhaeltnisDisposable?.dispose()
        deleteArbeitsverhaeltnisDisposable?.dispose()
    }
}
