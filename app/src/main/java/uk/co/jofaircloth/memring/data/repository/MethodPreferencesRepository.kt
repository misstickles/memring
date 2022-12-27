package uk.co.jofaircloth.memring.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

data class MethodPreferences(
    val showText: Boolean
)

class MethodPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private val TAG = "MethodPreferencesRepository"

    private object PreferencesKeys {
        val SHOW_TEXT_KEY = booleanPreferencesKey("show_text")
    }

    val methodPreferencesFlow: Flow<MethodPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapMethodPreferences(preferences)
        }

    suspend fun updateShowText(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_TEXT_KEY] = value
        }
    }

    suspend fun fetchInitialPreferences() =
        mapMethodPreferences(dataStore.data.first().toPreferences())

    private fun mapMethodPreferences(preferences: Preferences): MethodPreferences {
        val showText = preferences[PreferencesKeys.SHOW_TEXT_KEY] ?: true

        return MethodPreferences(
            showText = showText
        )
    }
}