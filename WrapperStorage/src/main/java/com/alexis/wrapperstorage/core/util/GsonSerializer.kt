package com.alexis.wrapperstorage.core.util

import com.alexis.wrapperstorage.core.model.ISerializer
import com.google.gson.Gson

class GsonSerializer : ISerializer {

    private val gson = Gson()

    override fun <T> serialize(obj: T): String {
        return gson.toJson(obj)
    }

    override fun <T> deserialize(obj: String?, defaultValue: T): T {
        return obj?.let { gson.fromJson(obj, defaultValue!!::class.java) } ?: defaultValue
    }

}