package de.esd.zeiterfassung.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class StartFragment : Fragment() {

    private lateinit var viewModel: StartZeiterfassungViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as? BaseActivity)?.let { activity ->
            initViewModel(activity)
            viewModel.existsConfiguration().observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer {
                if (it) {
                    ZeiterfassungNavigation.getNavigation(findNavController()).toUebersicht()
                } else {
                    ZeiterfassungNavigation.getNavigation(findNavController()).toConfig()
                }
            })
        }
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    private fun initViewModel(activity: BaseActivity) {
        viewModel = activity.provideViewModel(StartZeiterfassungViewModel::class.java)
    }


}