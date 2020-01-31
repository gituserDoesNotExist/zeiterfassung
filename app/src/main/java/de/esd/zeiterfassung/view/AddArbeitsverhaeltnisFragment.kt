package de.esd.zeiterfassung.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.esd.zeiterfassung.BaseActivity
import de.esd.zeiterfassung.R
import kotlinx.android.synthetic.main.fragment_add_arbeitsverhaeltnis.view.*


class AddArbeitsverhaeltnisFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_arbeitsverhaeltnis, container, false)
        val viewPager = rootView.viewpager

        (activity as? BaseActivity)?.let {
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        fragmentManager?.let {
            val adapter = AddArbeitsverhaeltnisViewPagerAdapter(it)
            viewPager.adapter = adapter
            val tabLayout = rootView.sliding_tabs
            tabLayout.setupWithViewPager(viewPager)

        }
        return rootView
    }


}
