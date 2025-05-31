package com.alexis.wrapperstorage.presentation.manager

import android.content.Context
import com.alexis.wrapperstorage.core.factory.StorageFactory
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.presentation.model.StorageConfig
import javax.inject.Inject

class StorageManagerFactory @Inject constructor(
    private val context: Context,
    private val serializer: ISerializer
) {
    fun createConfig(config: StorageConfig): StorageManager {
        val storageManager = StorageFactory.createStorage(context, config, serializer)
        return StorageManager(storageManager)
    }
}