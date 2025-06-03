package com.alexis.wrapperstorage.data.util.serializer.impl

import com.alexis.wrapperstorage.data.util.serializer.ISerializer
import com.google.gson.Gson

/**
 * Implementación de [ISerializer] que utiliza la librería Gson para serializar y deserializar objetos.
 *
 * Permite convertir objetos genéricos a su representación en cadena JSON y viceversa,
 * facilitando el almacenamiento y recuperación de datos en formato JSON.
 */
class GsonSerializer : ISerializer {

    private val gson = Gson()

    /**
     * Serializa un objeto a una cadena JSON.
     *
     * @param obj Objeto a serializar.
     * @return Representación JSON del objeto.
     */
    override fun <T> serialize(obj: T): String {
        return gson.toJson(obj)
    }

    /**
     * Deserializa una cadena JSON a un objeto del tipo especificado.
     *
     * @param obj Cadena JSON a deserializar.
     * @param defaultValue Valor por defecto en caso de error o si la cadena es nula.
     * @return Objeto deserializado o el valor por defecto.
     */
    override fun <T> deserialize(obj: String?, defaultValue: T): T {
        return obj?.let { gson.fromJson(obj, defaultValue!!::class.java) } ?: defaultValue
    }

}