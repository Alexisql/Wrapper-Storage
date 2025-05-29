package com.alexis.wrapperstorage.core.model

interface Serializer {
    fun <T> serialize(obj: T): String
    fun <T> deserialize(json: String, clazz: Class<T>): T
}