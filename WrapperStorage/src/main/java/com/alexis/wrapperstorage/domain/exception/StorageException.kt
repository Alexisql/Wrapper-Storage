package com.alexis.wrapperstorage.domain.exception

/**
 * Excepción base para errores relacionados con operaciones de almacenamiento de preferencias.
 *
 * Esta clase sellada agrupa las excepciones específicas que pueden ocurrir al interactuar con el almacenamiento,
 * como guardar, obtener o eliminar preferencias, así como errores al recuperar todas las preferencias de una pantalla.
 *
 * Las subclases proporcionan mensajes descriptivos para facilitar el diagnóstico de errores en las operaciones de almacenamiento.
 */
sealed class StorageException : RuntimeException() {

    data class PutException(val key: String, val value: String) :
        StorageException() {
        override val message: String =
            "Se presento un error al guardar la preferencia con clave $key y valor $value "
    }

    data class GetException(val key: String) : StorageException() {
        override val message: String =
            "Se presento un error al obtener la preferencia con clave $key"
    }

    data class RemoveException(val key: String) :
        StorageException() {
        override val message: String =
            "Se presento un error al eliminar la preferencia con clave $key"
    }

    data class GetPreferencesException(val screen: String) :
        StorageException() {
        override val message: String =
            "Se presento un error al obtener las preferencias de la pantalla $screen"
    }
}