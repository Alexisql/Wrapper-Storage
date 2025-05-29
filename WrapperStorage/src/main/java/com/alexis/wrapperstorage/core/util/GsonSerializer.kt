package com.alexis.wrapperstorage.core.util

import com.alexis.wrapperstorage.core.model.Serializer
import com.google.gson.Gson

class GsonSerializer : Serializer {

    private val gson = Gson()

    override fun <T> serialize(obj: T): String {
        return gson.toJson(obj)
    }

    override fun <T> deserialize(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }
}