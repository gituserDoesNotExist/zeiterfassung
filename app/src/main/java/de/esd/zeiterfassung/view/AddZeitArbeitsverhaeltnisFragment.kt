package de.esd.zeiterfassung.view


import android.view.View
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.R
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.ZeitArbeitsverhaeltnis
import io.reactivex.Single
import io.reactivex.disposables.Disposable


class AddZeitArbeitsverhaeltnisFragment : UpsertZeitArbeitsverhaeltnisFragment() {


    private var addArbeitseinsatzDisposable: Disposable? = null

    override fun fragmentTitle(): String {
        return resources.getString(R.string.title_add_arbeitsverhaeltnis_fragment)
    }

    override fun initArbeitsverhaeltnis(activity: BaseActivity) {
        upsertViewModel.initEventInfoAndArbeitsverhaeltnis(EventInfo(), ZeitArbeitsverhaeltnis())
    }

    override fun prepareView(rootView: View, config: CalendarConfiguration) {
        upsertViewModel.isUpdateMode.set(false)
        upsertViewModel.editable.set(true)
        upsertViewModel.setLeistungserbringer(config.appUser)
    }


    override fun upsert(): Single<Long> {
        return upsertViewModel.addArbeitsverhaeltnis()//
    }


    override fun onStop() {
        super.onStop()
        addArbeitseinsatzDisposable?.dispose()
    }
}
