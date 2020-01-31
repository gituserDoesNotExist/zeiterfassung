package de.esd.zeiterfassung

import com.google.android.material.snackbar.Snackbar


class EsdExceptionHandler(private val baseActivity: BaseActivity) : Thread.UncaughtExceptionHandler {

    private val rootHandler: Thread.UncaughtExceptionHandler =
        Thread.getDefaultUncaughtExceptionHandler() ?: throw RuntimeException("Something went wrong...")

    init {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        when (ex) {
            is EsdBaseException -> createSnackbar(ex.message ?: "Unknown error")
            else -> handleWrappedException(ex)
        }
    }

    private fun handleWrappedException(ex: Throwable) {
        baseActivity.hideProgressbarCallback()
        when (val cause = ex.cause) {
            is NoNetworkException -> {
                createSnackbar(cause.message ?: "").setAction("Try again") { baseActivity.reloadCallback() }.show()
            }
            is InvalidUserInputException -> {
                createSnackbar(cause.message ?: "", Snackbar.LENGTH_SHORT).show()
            }
            is EsdBaseException -> {
                createSnackbar("Unknown Error. Please Restart App.").show()
            }
            else -> {
                createSnackbar("This should not happen...")
            }
        }
    }

    private fun createSnackbar(charSequence: CharSequence, length: Int): Snackbar {
        return Snackbar.make(baseActivity.getParentViewForSnackbar(), charSequence, length)
    }

    private fun createSnackbar(charSequence: CharSequence) = createSnackbar(charSequence, Snackbar.LENGTH_INDEFINITE)


}