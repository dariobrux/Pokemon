package com.technicaltest.app.extensions

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.io.IOException

suspend inline fun <reified T> DataStore<Preferences>.getFromLocalStorage(PreferencesKey: Preferences.Key<T>, crossinline func: T.() -> Unit) {
    data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        it[PreferencesKey]
    }.collect {
        it?.let {
            func.invoke(it as T)
        }
    }
}

suspend inline fun <reified T> DataStore<Preferences>.storeValue(key: Preferences.Key<T>, value: Any) {
    this.edit {
        it[key] = value as T
    }
}

suspend inline fun <reified T> DataStore<Preferences>.readValue(key: Preferences.Key<T>, crossinline responseFunc: T.() -> Unit) {
    this.getFromLocalStorage(key) {
        responseFunc.invoke(this)
    }
}