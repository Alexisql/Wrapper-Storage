package com.alexis.wrapperstorage.domain.manager.impl

import android.util.Log
import com.alexis.wrapperstorage.domain.exception.StorageException
import com.alexis.wrapperstorage.domain.manager.IStorageManager
import com.alexis.wrapperstorage.domain.model.StorageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación de [IStorageManager] que actúa como fachada para el almacenamiento de datos.
 *
 * Esta clase delega todas las operaciones de almacenamiento a una instancia de [IStorageManager]
 * proporcionada por inyección de dependencias, permitiendo intercambiar fácilmente la implementación subyacente.
 *
 * Además, maneja las excepciones lanzadas durante las operaciones de almacenamiento y las transforma en
 * excepciones específicas de dominio ([StorageException]), facilitando el manejo de errores en capas superiores.
 *
 * @constructor Crea un StorageManager con la instancia de [IStorageManager] especificada.
 * @param storage Instancia de [IStorageManager] utilizada para las operaciones de almacenamiento.
 */
@Singleton
class StorageManager @Inject constructor(
    private val storage: IStorageManager
) : IStorageManager {

    override suspend fun <T> put(key: StorageKey<T>, value: T) {
        try {
            storage.put(key, value)
        } catch (exception: RuntimeException) {
            Log.e("Error Put", exception.stackTraceToString())
            throw StorageException.PutException(key.toString(), value.toString())
        }
    }

    override fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T> {
        try {
            return storage.get(key, defaultValue)
        } catch (exception: RuntimeException) {
            Log.e("Error Get", exception.stackTraceToString())
            throw StorageException.GetException(key.toString())
        }
    }

    override suspend fun remove(key: String) {
        try {
            storage.remove(key)
        } catch (exception: RuntimeException) {
            Log.e("Error Remove", exception.stackTraceToString())
            throw StorageException.RemoveException(key)
        }
    }

    override fun getPreferencesByScreen(screen: String): Flow<Map<String, *>> {
        try {
            return storage.getPreferencesByScreen(screen)
                .map { it.filter { (key, _) -> key.contains(screen) } }
        } catch (exception: RuntimeException) {
            Log.e("Error Get Preferences", exception.stackTraceToString())
            throw StorageException.GetPreferencesException(screen)
        }
    }

}