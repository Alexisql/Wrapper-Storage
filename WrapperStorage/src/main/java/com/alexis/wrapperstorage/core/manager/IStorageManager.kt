package com.alexis.wrapperstorage.core.manager

import com.alexis.wrapperstorage.presentation.model.StorageKey

interface IStorageManager {
    suspend fun <T> put(key: StorageKey<T>, value: T)
    suspend fun <T> get(key: StorageKey<T>, defaultValue: T): T
    suspend fun <T> remove(key: StorageKey<T>)
}