package com.alexis.wrapperstorage.presentation.manager

import android.content.Context
import com.alexis.wrapperstorage.core.factory.StorageFactory
import com.alexis.wrapperstorage.core.model.enums.StorageType
import com.alexis.wrapperstorage.core.repository.IStorageRepository
import com.alexis.wrapperstorage.presentation.model.StorageKey

class StorageManager private constructor(
    private val context: Context,
    private val storageType: StorageType,
    private val storageName: String
) {

    private val storageFactory: StorageFactory = StorageFactory(context)
    private val storage: IStorageRepository = storageFactory.getStorage(storageType, storageName)

    companion object {

        @Volatile
        private var INSTANCE: StorageManager? = null

        fun getInstance(
            context: Context,
            storageType: StorageType,
            storageName: String
        ): StorageManager {
            return INSTANCE ?: synchronized(this) {
                StorageManager(context, storageType, storageName).also { INSTANCE = it }
            }
        }
    }

    suspend fun <T> put(key: StorageKey<T>, value: T) {
        storage.put(key, value)
    }

    suspend fun <T> get(key: StorageKey<T>, defaultValue: T): T {
        return storage.get(key, defaultValue)
    }

    suspend fun <T> remove(key: StorageKey<T>) {
        storage.remove(key)
    }

}