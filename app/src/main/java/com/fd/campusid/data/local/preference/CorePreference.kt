package com.fd.campusid.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class CorePreference(private val context: Context) : ICorePreference {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override suspend fun save(key: String, value: String) {
        context.dataStore.edit { saveData ->
            saveData[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Int) {
        context.dataStore.edit { saveData ->
            saveData[intPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Boolean) {
        context.dataStore.edit { saveData ->
            saveData[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Float) {
        context.dataStore.edit { saveData ->
            saveData[floatPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Long) {
        context.dataStore.edit { saveData ->
            saveData[longPreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val data = context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
        return data.firstOrNull()
    }

    override suspend fun getInt(key: String): Int? {
        val data = context.dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)]
        }
        return data.firstOrNull()
    }

    override suspend fun getBoolean(key: String): Boolean? {
        val data = context.dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)]
        }
        return data.firstOrNull()
    }

    override suspend fun getFloat(key: String): Float? {
        val data = context.dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)]
        }
        return data.firstOrNull()
    }

    override suspend fun getLong(key: String): Long? {
        val data = context.dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)]
        }
        return data.firstOrNull()
    }

    override suspend fun remove(key: String) {
        context.dataStore.edit {
            it.remove(stringPreferencesKey(key))
            it.remove(intPreferencesKey(key))
            it.remove(booleanPreferencesKey(key))
            it.remove(floatPreferencesKey(key))
            it.remove(longPreferencesKey(key))
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

}