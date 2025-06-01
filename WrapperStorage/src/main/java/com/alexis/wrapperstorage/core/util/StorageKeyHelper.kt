package com.alexis.wrapperstorage.core.util

import com.alexis.wrapperstorage.presentation.model.StorageKey

/**
 * Carácter separador utilizado para construir y descomponer claves de almacenamiento en formato cadena.
 */
const val SEPARATOR = ","

/**
 * Utilidad interna para manipular y agrupar claves de almacenamiento.
 *
 * Proporciona métodos para convertir cadenas de claves en instancias de [StorageKey]
 * y agruparlas según la pantalla a la que pertenecen.
 */
internal object StorageKeyHelper {
    /**
     * Agrupa una colección de claves en formato cadena por el identificador de pantalla.
     *
     * Convierte cada cadena en una instancia de [StorageKey] si tiene el formato correcto,
     * y luego agrupa las claves por la propiedad `screen`.
     *
     * @param keys Colección de claves en formato cadena.
     * @return Mapa donde la clave es el identificador de pantalla y el valor es la lista de [StorageKey] asociadas.
     */
    fun groupKeysByScreen(
        keys: Collection<String>
    ): Map<String, List<StorageKey<*>>> {
        return keys.mapNotNull { key ->
            val parts = key.split(SEPARATOR)
            if (parts.size == 3) StorageKey<Any>(parts[0], parts[1], parts[2]) else null
        }.groupBy { it.screen }
    }
}