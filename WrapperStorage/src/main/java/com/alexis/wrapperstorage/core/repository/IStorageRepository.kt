package com.alexis.wrapperstorage.core.repository

import com.alexis.wrapperstorage.presentation.model.StorageKey

interface IStorageRepository {
    suspend fun <T> put(key: StorageKey<T>, value: T)
    suspend fun <T> get(key: StorageKey<T>, defaultValue: T): T
    suspend fun <T> remove(key: StorageKey<T>)
}