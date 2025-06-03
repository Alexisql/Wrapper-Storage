package com.alexis.wrapperstorage.domain.model

/**
 * Representa una clave compuesta para identificar valores almacenados de forma única.
 *
 * Combina el nombre, la pantalla y el cliente para formar una clave única utilizada en los mecanismos de almacenamiento.
 *
 * @param name Nombre lógico de la clave.
 * @param screen Identificador de la pantalla o contexto de uso.
 * @param client Identificador del cliente o usuario.
 * @param T Tipo de dato asociado a la clave.
 */
data class StorageKey<T>(val name: String, val screen: String, val client: String){
    override fun toString(): String {
        return "$name-$screen-$client"
    }
}

