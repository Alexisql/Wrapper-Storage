package com.alexis.wrapperstorage.core.model

/**
 * Interfaz para la serialización y deserialización de objetos genéricos.
 *
 * Permite convertir objetos a su representación en cadena y viceversa,
 * facilitando el almacenamiento y recuperación de datos en diferentes formatos.
 */
interface ISerializer {
    /**
     * Serializa un objeto a una cadena de texto.
     *
     * @param obj Objeto a serializar.
     * @return Representación en cadena del objeto.
     */
    fun <T> serialize(obj: T): String

    /**
     * Deserializa una cadena de texto a un objeto del tipo especificado.
     *
     * @param obj Cadena a deserializar.
     * @param defaultValue Valor por defecto en caso de error o si la cadena es nula.
     * @return Objeto deserializado o el valor por defecto.
     */
    fun <T> deserialize(obj: String?, defaultValue: T): T
}