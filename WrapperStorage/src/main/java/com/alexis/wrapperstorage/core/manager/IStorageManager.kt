package com.alexis.wrapperstorage.core.manager

import com.alexis.wrapperstorage.presentation.model.StorageKey
import kotlinx.coroutines.flow.Flow

interface IStorageManager {
    suspend fun <T> put(key: StorageKey<T>, value: T)
    fun <T> get(key: StorageKey<T>, defaultValue: T): Flow<T>
    suspend fun <T> remove(key: StorageKey<T>)
}