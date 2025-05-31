package com.alexis.wrapperstorage.core.factory

import android.content.Context
import com.alexis.wrapperstorage.core.model.enums.StorageTypeEnum
import com.alexis.wrapperstorage.core.manager.IStorageManager
import com.alexis.wrapperstorage.core.model.ISerializer
import com.alexis.wrapperstorage.data.local.datastore.StorageDataStore
import com.alexis.wrapperstorage.data.local.sharedpreferences.StorageSharedPreferences
import com.alexis.wrapperstorage.presentation.model.StorageConfig

internal class StorageFactory {
    companion object {
        fun createStorage(
            context: Context,
            config: StorageConfig,
            serializer: ISerializer
        ): IStorageManager {
            return when (config.storageType) {
                StorageTypeEnum.SHARED_PREFERENCES -> StorageSharedPreferences(
                    context,
                    config.storageName,
                    serializer
                )

                StorageTypeEnum.DATA_STORE -> StorageDataStore(
                    context,
                    config.storageName,
                    serializer
                )
            }
        }
    }
}