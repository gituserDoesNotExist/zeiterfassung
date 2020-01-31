package de.esd.zeiterfassung

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import de.esd.zeiterfassung.view.ZeiterfassungViewModelFactory
import io.reactivex.disposables.CompositeDisposable

val compositeDisposable = CompositeDisposable()

abstract class BaseActivity : AppCompatActivity() {

    var hideProgressbarCallback: () -> Unit = {}
    var reloadCallback: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EsdExceptionHandler(this)
    }

    abstract fun getParentViewForSnackbar() : View


    fun <T : ViewModel> provideViewModel(clazz: Class<T>): T {
        return ViewModelProviders.of(this,ZeiterfassungViewModelFactory(this.applicationContext)).get(clazz)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }
}