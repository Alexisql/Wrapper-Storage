package com.alexis.wrapperstorage.core.manager

import com.alexis.wrapperstorage.presentation.model.StorageKey

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
     * @return El valor almacenado o el valor por defecto.
     */
    suspend fun <T> get(key: StorageKey<T>, defaultValue: T): T

    /**
     * Elimina el valor asociado a la clave proporcionada.
     *
     * @param key Clave de almacenamiento a eliminar.
     */
    suspend fun remove(key: String)

    /**
     * Obtiene todas las preferencias almacenadas asociadas a una pantalla específica.
     *
     * Filtra las preferencias por el identificador de pantalla proporcionado y retorna un flujo con el resultado.
     *
     * @param screen Identificador de la pantalla para filtrar las preferencias.
     * @return Un mapa donde la clave es el nombre de la preferencia y el valor es el dato almacenado.
     */
    suspend fun getPreferencesByScreen(screen: String): Map<String, *>
}