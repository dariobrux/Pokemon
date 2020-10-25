package com.technicaltest.app

import android.service.autofill.Dataset
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import com.technicaltest.app.extensions.readValue
import com.technicaltest.app.preferences.PreferenceKeys
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest : TestCase() {

    @Mock
    lateinit var dataStore : DataStore<Preferences>

    @Mock
    lateinit var activity: MainActivity

    public override fun setUp() {
        super.setUp()
        MockitoAnnotations.openMocks(this)
    }

    public override fun tearDown() {}
}