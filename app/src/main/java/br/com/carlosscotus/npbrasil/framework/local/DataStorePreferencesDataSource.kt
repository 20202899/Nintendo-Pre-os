package br.com.carlosscotus.npbrasil.framework.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import br.com.carlosscotus.core.data.GameFilters
import br.com.carlosscotus.core.data.repository.StorageLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferencesDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : StorageLocalDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        PREFERENCES_DATA_STORE_NAME
    )

    private val filterKey = stringPreferencesKey(PREFERENCES_DATA_STORE_FILTER_KEY)

    override val sorting: Flow<String>
        get() = context.dataStore.data.map { store ->
            store[filterKey] ?: String()
        }

    override suspend fun saveSorting(sorting: String) {
        context.dataStore.edit { store ->
            store[filterKey] = sorting
        }
    }

    companion object {
        const val PREFERENCES_DATA_STORE_NAME = "DataStorePreferencesDataSource"
        const val PREFERENCES_DATA_STORE_FILTER_KEY = "filterKey"
    }
}