package de.esd.zeiterfassung.view


import android.view.View
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.R
import de.esd.zeiterfassung.core.EventInfo
import de.esd.zeiterfassung.core.StueckArbeitsverhaeltnis
import io.reactivex.Single
import io.reactivex.disposables.Disposable


class AddStueckArbeitsverhaeltnisFragment : UpsertStueckArbeitsverhaeltnisFragment() {


    private var addArbeitseinsatzDisposable: Disposable? = null


    override fun fragmentTitle(): String {
        return resources.getString(R.string.title_add_arbeitsverhaeltnis_fragment)
    }

    override fun initArbeitsverhaeltnis(activity: BaseActivity) {
        viewModel.initEventInfoAndArbeitsverhaeltnis(EventInfo(), StueckArbeitsverhaeltnis())
    }

    override fun prepareView(rootView: View, config: CalendarConfiguration) {
        viewModel.isUpdateMode.set(false)
        viewModel.editable.set(true)
        viewModel.setLeistungserbringer(config.appUser)
    }


    override fun upsert(): Single<Long> {
        return viewModel.addArbeitsverhaeltnis()
    }

    override fun onStop() {
        super.onStop()
        addArbeitseinsatzDisposable?.dispose()
    }
}
