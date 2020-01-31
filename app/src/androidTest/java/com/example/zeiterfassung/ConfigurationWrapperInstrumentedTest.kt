package de.esd.zeiterfassung

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented daysWithoutChocolate, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ConfigurationWrapperInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under daysWithoutChocolate.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("de.esd.zeiterfassung", appContext.packageName)
    }
}
