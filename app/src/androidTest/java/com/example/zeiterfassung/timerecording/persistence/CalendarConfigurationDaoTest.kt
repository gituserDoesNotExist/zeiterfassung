package de.esd.zeiterfassung.persistence

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import de.esd.zeiterfassung.persistence.AppDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalendarConfigurationDaoTest {

    private lateinit var testCandidate: CalendarConfigurationDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        testCandidate = database.calendarConfigurationDao()
    }

    @Test
    fun testSaveConfiguration() {
        val config = CalendarConfigurationEntity()

        testCandidate.insertConfiguration(config)
    }

}