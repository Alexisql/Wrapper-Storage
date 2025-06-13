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
import com.alexis.wrapperstorage.data.util.serializer.ISerializer
import com.alexis.wrapperstorage.domain.manager.IStorageManager
import com.alexis.wrapperstorage.domain.model.StorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementación de [IStorageManager] que utiliza Jetpack DataStore para la persistencia de datos clave-valor.
 *
 * Permite almacenar, recuperar y eliminar datos de diferentes tipos, serializando objetos complejos mediante [ISerializer].
 *
 * @property context Contexto de la aplicación.
 * @property storageName Nombre del DataStore.
 * @property serializer Serializador para objetos personalizados.
 */

class StorageDataStore @Inject constructor(
    private val context: Context,
    private val storageName: String,
    private val serializer: ISerializer
) : IStorageManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(storageName)
    private val storage = context.dataStore

    /**
     * Guarda un valor asociado a la clave proporcionada en DataStore.
     *
     * @param key Clave de almacenamiento.
     * @param value Valor a guardar.
     */
    override suspend fun <T> put(key: StorageKey<T>, value: T) {
        val fullKey = key.toString()
        storage.edit { preferences ->
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

    /**
     * Obtiene el valor asociado a la clave proporcionada desde DataStore.
     *
     * @param key Clave de almacenamiento.
     * @param defaultValue Valor por defecto si la clave no existe.
     * @return Un [Flow] que emite el valor almacenado o el valor por defecto.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T> {
        val fullKey = key.toString()
        return storage.data.map { preferences ->
            when (defaultValue) {
                is Int -> (preferences[intPreferencesKey(fullKey)] ?: defaultValue) as T
                is Long -> (preferences[longPreferencesKey(fullKey)] ?: defaultValue) as T
                is Boolean -> (preferences[booleanPreferencesKey(fullKey)] ?: defaultValue) as T
                is Float -> (preferences[floatPreferencesKey(fullKey)] ?: defaultValue) as T
                is Double -> (preferences[doublePreferencesKey(fullKey)] ?: defaultValue) as T
                is String -> (preferences[stringPreferencesKey(fullKey)] ?: defaultValue) as T
                else -> serializer.deserialize(
                    preferences[stringPreferencesKey(fullKey)], defaultValue
                )
            }
        }
    }

    /**
     * Elimina el valor asociado a la clave proporcionada en DataStore.
     *
     * @param key Clave de almacenamiento a eliminar.
     */
    override suspend fun remove(key: String) {
        storage.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

    /**
     * Obtiene todas las preferencias almacenadas asociadas a una pantalla específica.
     *
     * Filtra las preferencias por el identificador de pantalla proporcionado y retorna un flujo con el resultado.
     *
     * @param screen Identificador de la pantalla para filtrar las preferencias.
     * @return Un [Flow] que emite un mapa donde la clave es el nombre de la preferencia y el valor es el dato almacenado.
     */
    override fun getPreferencesByScreen(screen: String): Flow<Map<String, *>> {
        return storage.data.map { preferences ->
            preferences.asMap().mapKeys { it.key.name }
        }
    }

}