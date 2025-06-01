package com.alexis.wrapperstorage.core.manager

import com.alexis.wrapperstorage.presentation.model.StorageKey
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz que define las operaciones básicas para la gestión de almacenamiento clave-valor.
 *
 * Permite guardar, obtener y eliminar datos de forma genérica, utilizando una clave de tipo [StorageKey].
 */
interface IStorageManager {
    /**
     * Guarda un valor asociado a la clave proporcionada.
     *
     * @param key Clave de almacenamiento.
     * @param value Valor a guardar.
     */
    suspend fun <T> put(key: StorageKey<T>, value: T)

    /**
     * Obtiene el valor asociado a la clave proporcionada.
     *
     * @param key Clave de almacenamiento.
     * @param defaultValue Valor por defecto si la clave no existe.
     * @return Un [Flow] que emite el valor almacenado o el valor por defecto.
     */
    fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T>

    /**
     * Elimina el valor asociado a la clave proporcionada.
     *
     * @param key Clave de almacenamiento a eliminar.
     */
    suspend fun <T> remove(key: StorageKey<T>)

    /**
     * Obtiene todas las claves almacenadas, agrupadas por pantalla.
     *
     * @return Un [Flow] que emite un mapa donde la clave es el identificador de pantalla y el valor es la lista de [StorageKey].
     */
    fun getAllKeys(): Flow<Map<String, List<StorageKey<*>>>>
}