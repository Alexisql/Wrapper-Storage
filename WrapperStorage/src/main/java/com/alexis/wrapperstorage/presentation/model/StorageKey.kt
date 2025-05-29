package com.alexis.wrapperstorage.presentation.model

data class StorageKey<T>(val name: String, val screen: String, val client: String) {
    fun fullKey(): String {
        return "$name-$screen-$client"
    }
}
