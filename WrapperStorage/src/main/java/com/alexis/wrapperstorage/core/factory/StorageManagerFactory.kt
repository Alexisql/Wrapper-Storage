package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.presentation.manager.StorageManager
import com.alexis.wrapperstorage.presentation.model.StorageConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StorageManagerFactory @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun createConfig(config: StorageConfig): StorageManager {
        val storageManager = StorageFactory().createStorage(context, config.storageType, config.storageName)
        return StorageManager(storageManager)
    }
}