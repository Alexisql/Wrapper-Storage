package com.alexis.wrapperstorage.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.presentation.model.StorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StorageDataStore(
    private val context: Context,
    private val storageName: String,
    private val serializer: ISerializer
) : IStorageManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(storageName)
    private val dataStore = context.dataStore

    override suspend fun <T> put(key: StorageKey<T>, value: T) {
        val fullKey = key.fullKey()
        dataStore.edit { preferences ->
            when (value) {
                is Int -> preferences[intPreferencesKey(fullKey)] = value
                is Long -> preferences[longPreferencesKey(fullKey)] = value
                is Boolean -> preferences[booleanPreferencesKey(fullKey)] = value
                is Float -> preferences[floatPreferencesKey(fullKey)] = value
                is Double -> preferences[doublePreferencesKey(fullKey)] = value
                is String -> preferences[stringPreferencesKey(fullKey)] = value
                else -> preferences[stringPreferencesKey(fullKey)] = serializer.serialize(value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T> {
        val fullKey = key.fullKey()
        return dataStore.data.map { preferences ->
            when (defaultValue) {
                is Int -> (preferences[intPreferencesKey(fullKey)] ?: defaultValue) as T
                is Long -> (preferences[longPreferencesKey(fullKey)] ?: defaultValue) as T
                is Boolean -> (preferences[booleanPreferencesKey(fullKey)] ?: defaultValue) as T
                is Float -> (preferences[floatPreferencesKey(fullKey)] ?: defaultValue) as T
                is Double -> (preferences[doublePreferencesKey(fullKey)] ?: defaultValue) as T
                is String -> (preferences[stringPreferencesKey(fullKey)] ?: defaultValue) as T
                else -> serializer.deserialize(
                    preferences[stringPreferencesKey(fullKey)],
                    defaultValue
                )
            }
        }
    }

    override suspend fun <T> remove(key: StorageKey<T>) {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key.fullKey()))
        }
    }

}