package com.alexis.wrapperstorage.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.core.util.StorageKeyHelper
import com.alexis.wrapperstorage.presentation.model.StorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * Implementación de [IStorageManager] que utiliza SharedPreferences para la persistencia de datos clave-valor.
 *
 * Permite almacenar, recuperar y eliminar datos de diferentes tipos, serializando objetos complejos mediante [ISerializer].
 *
 * @property context Contexto de la aplicación.
 * @property storageName Nombre del archivo de SharedPreferences.
 * @property serializer Serializador para objetos personalizados.
 */
class StorageSharedPreferences @Inject constructor(
    private val context: Context,
    private val storageName: String,
    private val serializer: ISerializer
) : IStorageManager {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(storageName, Context.MODE_PRIVATE)
    }

    /**
     * Guarda un valor asociado a la clave proporcionada en SharedPreferences.
     *
     * @param key Clave de almacenamiento.
     * @param value Valor a guardar.
     */
    override suspend fun <T> put(key: StorageKey<T>, value: T) {
        val fullKey = key.toString()
        sharedPreferences.edit(commit = true) {
            when (value) {
                is String -> putString(fullKey, value)
                is Int -> putInt(fullKey, value)
                is Boolean -> putBoolean(fullKey, value)
                is Float -> putFloat(fullKey, value)
                is Long -> putLong(fullKey, value)
                else -> putString(fullKey, serializer.serialize(value))
            }
        }
    }

    /**
     * Obtiene el valor asociado a la clave proporcionada desde SharedPreferences.
     *
     * @param key Clave de almacenamiento.
     * @param defaultValue Valor por defecto si la clave no existe.
     * @return Un [Flow] que emite el valor almacenado o el valor por defecto.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T> {
        val fullKey = key.toString()
        val value: T = when (defaultValue) {
            is String -> sharedPreferences.getString(fullKey, defaultValue) as T
            is Int -> sharedPreferences.getInt(fullKey, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(fullKey, defaultValue) as T
            is Float -> sharedPreferences.getFloat(fullKey, defaultValue) as T
            is Long -> sharedPreferences.getLong(fullKey, defaultValue) as T
            else -> serializer.deserialize(sharedPreferences.getString(fullKey, null), defaultValue)
        }
        return flowOf(value)
    }

    /**
     * Elimina el valor asociado a la clave proporcionada en SharedPreferences.
     *
     * @param key Clave de almacenamiento a eliminar.
     */
    override suspend fun <T> remove(key: StorageKey<T>) {
        sharedPreferences.edit { remove(key.toString()) }
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
        return flowOf(StorageKeyHelper.filterPreferencesByScreen(sharedPreferences.all, screen))
    }

}