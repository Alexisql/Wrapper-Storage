package com.alexis.wrapperstorage.core.model

interface ISerializer {
    fun <T> serialize(obj: T): String
    fun <T> deserialize(obj: String?, defaultValue: T): T
}